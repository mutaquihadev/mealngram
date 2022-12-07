package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.domain.WEEK
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class RecipeListUiData(
    val showButton: Boolean = false,
    val showProgress: Boolean = false,
    val progressValue: Int = 0,
    val recipes: List<RecipeItem>
)

sealed interface RecipeListUiState {
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
    data class Success(val uiData: RecipeListUiData) : RecipeListUiState
}

interface RecipeListViewModelItemActions {
    fun onDaySelected(recipeId: Int, weekDay: WEEK, date: Date)
}

@HiltViewModel
class RecipeListViewModel @Inject constructor(repository: RecipesRepository) : ViewModel(),
    RecipeListViewModelItemActions {

    private var isEditMode: Boolean = false

    private val _uiState: MutableStateFlow<RecipeListUiState> =
        MutableStateFlow(RecipeListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val selectedChipStates: List<SelectedChip> by lazy {
        val calendar = Calendar.getInstance()
        calendar.daysUntilTheEndOfWeek().map {
            SelectedChip(date = it, weekDay = it.toWeek())
        }
    }

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
        }
    }

    private fun handleGetAllRecipes(state: DataState<List<RecipeItem>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Data -> {
                _uiState.value =
                    RecipeListUiState.Success(uiData = RecipeListUiData(recipes = state.data))
            }
            is DataState.Loading -> _uiState.value = RecipeListUiState.Loading
        }
    }

    fun updateEditMode() {
        isEditMode = !isEditMode

        _uiState.value.let {
            when(it) {
                RecipeListUiState.Error -> {}
                RecipeListUiState.Loading -> {}
                is RecipeListUiState.Success -> {
                    val updatedRecipes = it.uiData.recipes.map { recipe ->
                        RecipeItem(
                            id = recipe.id,
                            name = recipe.name,
                            description = recipe.description,
                            ingredients = recipe.ingredients,
                            image = recipe.image,
                            isSelectionMode = isEditMode,
                            selectedDays = recipe.selectedDays
                        )
                    }

                    _uiState.value = RecipeListUiState.Success(
                        uiData = RecipeListUiData(recipes = updatedRecipes, showProgress = isEditMode)
                    )
                }
            }
        }
    }

    private fun clearSelectionMode() {
//        val clearedRecipes = recipes.map { recipe ->
//            RecipeItem(
//                id = recipe.id,
//                name = recipe.name,
//                description = recipe.description,
//                ingredients = recipe.ingredients,
//                image = recipe.image,
//                isSelectionMode = false,
//                selectedDays = recipe.selectedDays
//            )
//        }
//
//        recipes.clear()
//        recipes.addAll(clearedRecipes)
    }


    private fun clearSelectedRecipes() {
        selectedChipStates.forEach { it.recipeId = null }
    }


    override fun onDaySelected(recipeId: Int, weekDay : WEEK, date : Date) {

        _uiState.value.let {
            when (it) {
                RecipeListUiState.Error -> {}
                RecipeListUiState.Loading -> {}
                is RecipeListUiState.Success -> {
                    val recipe = it.uiData.recipes.first { recipeItem -> recipeItem.id == recipeId }
                    val selectableDays = recipe.selectedDays

                    val isCurrentSelectedDayChecked = selectableDays.first { selectableDay ->  selectableDay.week == weekDay }.isChecked
                    val isCurrentSelectedDayCheckedUpdated = !isCurrentSelectedDayChecked

                    val selected = selectedChipStates.first { selectedChipState -> selectedChipState.weekDay == weekDay }

                    val (updatedRecipeId, updatedIsChecked) = if (isCurrentSelectedDayCheckedUpdated) Pair(recipeId, true) else Pair(null,false)
                    selected.recipeId = updatedRecipeId
                    selected.isChecked = updatedIsChecked

                    val updatedRecipes = it.uiData.recipes.map { recipeItem ->

                        val updatedSelectedDays = selectedChipStates.toSelectedChipStates(recipeItemId = recipeItem.id)

                        RecipeItem(
                            id = recipeItem.id,
                            name = recipeItem.name,
                            description = recipeItem.description,
                            ingredients = recipe.ingredients,
                            image = recipeItem.image,
                            isSelectionMode = true,
                            selectedDays = updatedSelectedDays
                        )
                    }

                    _uiState.value = RecipeListUiState.Success(
                        uiData = RecipeListUiData(recipes = updatedRecipes, showProgress = true)
                    )
                }
            }
        }
    }
}

data class SelectedChip(val date : Date, val weekDay: WEEK,  var recipeId: Int? = null, var isChecked : Boolean = false)

fun List<SelectedChip>.toSelectedChipStates(recipeItemId: Int) : List<SelectedChipState> {
    return this.map {  selectedChip ->

        val chipState = if(selectedChip.recipeId == null) {
            ChipStateOptions.NOT_SELECTED
        } else if (selectedChip.recipeId != recipeItemId){
            ChipStateOptions.DISABLED
        } else  {
            if(selectedChip.isChecked) {
                ChipStateOptions.SELECTED
            } else {
                ChipStateOptions.NOT_SELECTED
            }
        }

         when(chipState) {
            ChipStateOptions.NOT_SELECTED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = true,
                    isChecked = false
                )
            }
            ChipStateOptions.SELECTED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = true,
                    isChecked = true
                )
            }
            ChipStateOptions.DISABLED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = false,
                    isChecked = false
                )
            }
        }
    }
}

enum class ChipStateOptions{
    NOT_SELECTED,
    SELECTED,
    DISABLED
}