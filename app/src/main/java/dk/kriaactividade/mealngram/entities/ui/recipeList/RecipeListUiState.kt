package dk.kriaactividade.mealngram.entities.ui.recipeList

sealed interface RecipeListUiState {
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
    data class Success(val uiData: RecipeListUiData) : RecipeListUiState
}