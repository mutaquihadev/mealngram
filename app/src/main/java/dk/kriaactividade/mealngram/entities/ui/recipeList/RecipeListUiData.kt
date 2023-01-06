package dk.kriaactividade.mealngram.entities.ui.recipeList

import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.entities.domain.recipe.RecipeItem

data class RecipeListUiData(
    val showButton: Boolean = false,
    val progressValue: Int = 0,
    val recipes: List<RecipeItem> = listOf(),
    val completeSelection: MutableList<SelectableRecipe> = mutableListOf()
)