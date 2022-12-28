package dk.kriaactividade.mealngram.entities.domain.recipe

data class RecipesSelectedItem(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val day: String? = null,
    val dayOfWeek: WEEK? = null,
    val ingredients: List<String>
)