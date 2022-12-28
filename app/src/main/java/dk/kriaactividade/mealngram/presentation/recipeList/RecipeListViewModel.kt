package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.entities.domain.*
import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChip
import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChipState
import dk.kriaactividade.mealngram.entities.domain.chip.toSelectedChipStates
import dk.kriaactividade.mealngram.entities.domain.recipe.RecipeItem
import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListUiData
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListUiState
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListViewModelItemActions
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val repository: RecipesRepository, private val savedStateHandle: SavedStateHandle) :
    ViewModel(),
    RecipeListViewModelItemActions {

    private val _uiState: MutableStateFlow<RecipeListUiState> =
        MutableStateFlow(RecipeListUiState.Loading)

    val uiState = _uiState.asStateFlow()

    private val dateTimeLong
        get() = savedStateHandle["weekNumber"] ?: Calendar.getInstance().time.time

    private val selectedChipStates: List<SelectedChip> by lazy {
        val calendar = Calendar.getInstance()
        calendar.time = Date(dateTimeLong)
        calendar.daysUntilTheEndOfWeek().map {
            SelectedChip(date = it, weekDay = it.toWeek())
        }
    }

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
        }
    }

    private fun generateSelectedDays(recipeId: Int, date : Date): List<SelectedChipState> {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.daysUntilTheEndOfWeek().map {
            SelectedChipState(id = recipeId, date = it, week = it.toWeek())
        }
    }

    private fun handleGetAllRecipes(state: DataState<List<RecipeEntity>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Data -> {
                val recipeItem = state.data.map { recipe ->
                    RecipeItem(
                        id = recipe.id,
                        name = recipe.name,
                        description = recipe.description,
                        image = recipe.image,
                        ingredients = recipe.ingredients,
                        selectedDays = generateSelectedDays(recipe.id, Date(dateTimeLong))
                    )
                }

                _uiState.value =
                    RecipeListUiState.Success(uiData = RecipeListUiData(recipes = recipeItem))
            }
            is DataState.Loading -> _uiState.value = RecipeListUiState.Loading
        }
    }

    override fun onDaySelected(recipeId: Int, weekDay: WEEK, date: Date) {
        _uiState.value.let {
            when (it) {
                RecipeListUiState.Error -> {}
                RecipeListUiState.Loading -> {}
                is RecipeListUiState.Success -> {
                    val recipe = it.uiData.recipes.first { recipeItem -> recipeItem.id == recipeId }
                    val selectableDays = recipe.selectedDays

                    val isCurrentSelectedDayChecked =
                        selectableDays.first { selectableDay -> selectableDay.week == weekDay }.isChecked
                    val isCurrentSelectedDayCheckedUpdated = !isCurrentSelectedDayChecked

                    val selected =
                        selectedChipStates.first { selectedChipState -> selectedChipState.weekDay == weekDay }

                    val (updatedRecipeId, updatedIsChecked) = if (isCurrentSelectedDayCheckedUpdated) Pair(
                        recipeId,
                        true
                    ) else Pair(null, false)
                    selected.recipeId = updatedRecipeId
                    selected.isChecked = updatedIsChecked

                    val updatedRecipes = it.uiData.recipes.map { recipeItem ->

                        val updatedSelectedDays =
                            selectedChipStates.toSelectedChipStates(recipeItemId = recipeItem.id)

                        RecipeItem(
                            id = recipeItem.id,
                            name = recipeItem.name,
                            description = recipeItem.description,
                            ingredients = recipe.ingredients,
                            image = recipeItem.image,
                            selectedDays = updatedSelectedDays
                        )
                    }


                    _uiState.value = RecipeListUiState.Success(
                        uiData = RecipeListUiData(
                            recipes = updatedRecipes,
                            progressValue = 0,
                            showButton = false
                        )
                    )

                }
            }
        }
    }

    fun saveSelectedRecipes(){
        viewModelScope.launch {
            //repository.saveSelectedRecipes(selectedList)
        }
    }
}