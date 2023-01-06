package dk.kriaactividade.mealngram.entities.ui.currentDayRecipe

import dk.kriaactividade.mealngram.database.room.SelectableRecipe

sealed interface RecipeOfDayUiState {
    object Empty : RecipeOfDayUiState
    data class Success(val currentDayRecipe: SelectableRecipe) : RecipeOfDayUiState
}