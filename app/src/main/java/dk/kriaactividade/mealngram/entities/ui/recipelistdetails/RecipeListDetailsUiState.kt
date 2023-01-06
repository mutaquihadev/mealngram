package dk.kriaactividade.mealngram.entities.ui.recipelistdetails

import dk.kriaactividade.mealngram.database.room.SelectableRecipe

sealed interface RecipeListDetailsUiState {
    object Loading : RecipeListDetailsUiState
    object Error : RecipeListDetailsUiState
    data class Success(val recipes: List<SelectableRecipe>) : RecipeListDetailsUiState
}