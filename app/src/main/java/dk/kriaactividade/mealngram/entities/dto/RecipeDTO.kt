package dk.kriaactividade.mealngram.entities.dto

data class RecipeDTO(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = listOf(),
    val image: String = ""
)
