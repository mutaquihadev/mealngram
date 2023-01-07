package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChipState
import dk.kriaactividade.mealngram.entities.domain.extensions.daysUntilTheEndOfWeek
import dk.kriaactividade.mealngram.entities.domain.recipe.RecipeItem
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListUiData
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListUiState
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListViewModelItemActions
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.presentation.utils.Constants.PROGRESS_MAX
import dk.kriaactividade.mealngram.presentation.utils.isSameDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipesRepository, private val savedStateHandle: SavedStateHandle
) : ViewModel(), RecipeListViewModelItemActions {

    private val _uiState: MutableStateFlow<RecipeListUiState> =
        MutableStateFlow(RecipeListUiState.Loading)

    val uiState = _uiState.asStateFlow()

    private var count = 0

    private val dateTimeLong
        get() = savedStateHandle["weekNumber"] ?: Calendar.getInstance().time.time

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
        }
    }

    private fun generateSelectedDays(recipeId: Int, date: Date): List<SelectedChipState> {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.daysUntilTheEndOfWeek().map {
            SelectedChipState(id = recipeId, date = it)
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

    override fun onSelectableDaySelected(selectableDay: SelectedChipState) {
        recipesFromState { recipeItems ->

            val updatedSelectableDay = updateSelectedDay(selectableDay)

            val updatedRecipes = recipeItems.map { recipeItem ->
                val updatedSelectedDays = updateSelectableDays(recipeItem, updatedSelectableDay)
                updatedRecipeItem(recipeItem, updatedSelectedDays)
            }

            val progress = updateProgress(updatedRecipes, selectableDay)

            val showButton = count > PROGRESS_MAX

            _uiState.value = RecipeListUiState.Success(
                uiData = RecipeListUiData(
                    recipes = updatedRecipes,
                    progressValue = progress,
                    showButton = showButton
                )
            )
        }
    }

    private fun updateProgress(
        recipeItems: List<RecipeItem>,
        selectableDay: SelectedChipState
    ): Int {
        val listDays = mutableListOf<SelectedChipState>()
        recipeItems.forEach { recipeItem ->
            if (recipeItem.id == selectableDay.id) {
                listDays.addAll(recipeItem.selectedDays)
            }
        }
        return if (!selectableDay.isChecked) {
            count += PROGRESS_MAX / listDays.size + 1
            count
        } else {
            count -= PROGRESS_MAX / listDays.size + 1
            count
        }
    }

    private fun updatedRecipeItem(
        recipeItem: RecipeItem,
        updatedSelectedDays: List<SelectedChipState>
    ) = RecipeItem(
        id = recipeItem.id,
        name = recipeItem.name,
        description = recipeItem.description,
        ingredients = recipeItem.ingredients,
        image = recipeItem.image,
        selectedDays = updatedSelectedDays
    )

    private fun updateSelectableDays(
        recipeItem: RecipeItem,
        updatedSelectableDay: SelectedChipState
    ) = recipeItem.selectedDays.map { selectedChipState ->
        if (selectedChipState.date.isSameDay(updatedSelectableDay.date)) {

            if (recipeItem.id == updatedSelectableDay.id) {
                updatedSelectableDay
            } else {
                val isChecked = false
                val isSelectable = !updatedSelectableDay.isChecked

                SelectedChipState(
                    id = selectedChipState.id,
                    isSelectable = isSelectable,
                    isChecked = isChecked,
                    date = selectedChipState.date
                )
            }

        } else {
            selectedChipState
        }
    }

    private fun updateSelectedDay(selectableDay: SelectedChipState) = SelectedChipState(
        id = selectableDay.id,
        isSelectable = true,
        isChecked = !selectableDay.isChecked,
        date = selectableDay.date
    )

    private fun recipesFromState(onRecipeItems: (List<RecipeItem>) -> Unit) {
        _uiState.value.let {
            when (it) {
                RecipeListUiState.Error -> {}
                RecipeListUiState.Loading -> {}
                is RecipeListUiState.Success -> {
                    onRecipeItems(it.uiData.recipes)
                }
            }
        }
    }

    fun saveSelectedRecipes() {
        viewModelScope.launch {
            //repository.saveSelectedRecipes(selectedList)
        }
    }
}