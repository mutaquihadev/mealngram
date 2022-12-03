package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.ChipState
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.WEEK
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

data class RecipeListUiData(
    val showButton: Boolean = false,
    val showProgress: Boolean = false,
    val progressValue: Int = 0,
    val recipes: List<Recipe>
)

sealed interface RecipeListUiState {
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
    data class Success(val uiData: RecipeListUiData) : RecipeListUiState
}

class RecipeListViewModel @Inject constructor(repository: RecipesRepository) : ViewModel() {

    private var isEditMode: Boolean = false

    private val _uiState: MutableStateFlow<RecipeListUiState> =
        MutableStateFlow(RecipeListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val recipes = mutableListOf<Recipe>()

    private val selectedChipStates = listOf(
        SelectedChip(WEEK.MONDAY),
        SelectedChip(WEEK.TUESDAY),
        SelectedChip(WEEK.WEDNESDAY),
        SelectedChip(WEEK.THURSDAY),
        SelectedChip(WEEK.FRIDAY),
        SelectedChip(WEEK.SATURDAY),
        SelectedChip(WEEK.SUNDAY)
    )

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
        }
    }

    private fun handleGetAllRecipes(state: DataState<List<Recipe>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Data -> {
                recipes.clear()
                recipes.addAll(state.data)
                _uiState.value =
                    RecipeListUiState.Success(uiData = RecipeListUiData(recipes = recipes))
            }
            is DataState.Loading -> _uiState.value = RecipeListUiState.Loading
        }
    }

    fun updateEditMode() {
        isEditMode = !isEditMode

        val updatedRecipes = recipes.map { recipe ->
            Recipe(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients,
                image = recipe.image,
                video = recipe.video,
                mainImage = recipe.mainImage,
                isSelectionMode = isEditMode
            )
        }

        _uiState.value = RecipeListUiState.Success(
            uiData = RecipeListUiData(recipes = updatedRecipes, showProgress = isEditMode)
        )

        if(!isEditMode) {
            clearSelectedRecipes()
            clearSelectionMode()
        }
    }

    private fun clearSelectionMode() {
        val clearedRecipes = recipes.map { recipe ->
            Recipe(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients,
                image = recipe.image,
                video = recipe.video,
                mainImage = recipe.mainImage,
                isSelectionMode = false,
            )
        }

        recipes.clear()
        recipes.addAll(clearedRecipes)
    }


    private fun clearSelectedRecipes() {
        selectedChipStates.forEach { it.recipeId = null }
    }

    fun onChipSelected(recipeId: Int, weekDay: WEEK, selectedState: Boolean) {
        val updatedSelectedState = !selectedState

        selectedChipStates.first { it.weekDay == weekDay }.recipeId =
            if (updatedSelectedState) recipeId else null

        val updatedRecipes = recipes.map { recipe ->

            val updatedChipStates = selectedChipStates.mapIndexed { index, selectedChipState ->
                updateChipStates(recipe, index, selectedChipState)
            }

            Recipe(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients,
                image = recipe.image,
                video = recipe.video,
                mainImage = recipe.mainImage,
                isSelectionMode = true,
                dayOfWeekSelectedPair = updatedChipStates
            )
        }

        recipes.clear()
        recipes.addAll(updatedRecipes)

        val progressValue = ((selectedChipStates.filter { it.recipeId != null }.size.toFloat() / selectedChipStates.size.toFloat()) * 100).roundToInt()
        val showButton = progressValue == 100

        _uiState.value = RecipeListUiState.Success(
            uiData = RecipeListUiData(
                recipes = recipes,
                showProgress = true,
                showButton = showButton,
                progressValue = progressValue
            )
        )
    }

    private fun updateChipStates(
        recipe: Recipe,
        index: Int,
        selectedChipState: SelectedChip,
    ): ChipState {
        val recipeChipState = recipe.dayOfWeekSelectedPair[index]
        return selectedChipState.recipeId?.let { selectedId ->

            if (selectedId == recipe.id) {
                ChipState(
                    id = recipeChipState.id,
                    isActive = !recipeChipState.isActive,
                    isVisible = true,
                    dayOfWeek = recipeChipState.dayOfWeek
                )

            } else {
                ChipState(
                    id = recipeChipState.id,
                    isActive = false,
                    isVisible = false,
                    dayOfWeek = recipeChipState.dayOfWeek
                )

            }

        } ?: ChipState(id = recipeChipState.id, dayOfWeek = recipeChipState.dayOfWeek)
    }
}

data class SelectedChip(val weekDay: WEEK, var recipeId: Int? = null)