package dk.kriaactividade.mealngram.data.domain

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val images: List<String>,
    val video: String?,
    val mainImage: String? = null,
    val isSelectionMode: Boolean = false,
) {
    val dayOfWeekSelectedPair: List<Pair<String, Boolean>>
        get() = listOf(
            Pair("M", false),
            Pair("T", false),
            Pair("W", false),
            Pair("Th", false),
            Pair("F", false),
            Pair("Sa", false),
            Pair("Su", false)
        )

}