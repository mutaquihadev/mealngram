package dk.kriaactividade.mealngram.entities.domain.recipe


data class SelectFavoriteItem(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val isFavorite: Boolean = false,
    val ingredients: List<String>
)