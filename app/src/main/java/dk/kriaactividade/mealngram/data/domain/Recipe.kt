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
    val dayOfWeekSelectedPair: List<ChipState>
        get() = listOf(
            ChipState("M"),
            ChipState("T"),
            ChipState("W"),
            ChipState("Th"),
            ChipState("F"),
            ChipState("Sa"),
            ChipState("Su")
        )

}

data class ChipState( val dayOfWeek:String,  val isActive:Boolean = false, val isVisible:Boolean = true)