package dk.kriaactividade.mealngram.presentation.recipesSelected

import dk.kriaactividade.mealngram.data.domain.RecipeDTO
import dk.kriaactividade.mealngram.data.domain.WEEK

data class RecipesSelectedItem(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val day: String? = null,
    val dayOfWeek: WEEK? = null,
    val ingredients: List<String>
)

fun List<RecipeDTO>.toRecipeSelectedItem(): List<RecipesSelectedItem> {
    return this.map { recipe ->
        RecipesSelectedItem(
            id = recipe.id,
            name = recipe.name,
            image = recipe.image,
            description = recipe.description,
            ingredients = recipe.ingredients,
        )
    }
}