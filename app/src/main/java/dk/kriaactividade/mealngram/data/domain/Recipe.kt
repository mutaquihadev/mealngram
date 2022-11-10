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
    var dayOfWeekSelectedPair: List<ChipState> = listOf(
        ChipState(id = id, WEEK.MONDAY),
        ChipState(id = id, WEEK.TUESDAY),
        ChipState(id = id, WEEK.WEDNESDAY),
        ChipState(id = id, WEEK.THURSDAY),
        ChipState(id = id, WEEK.FRIDAY),
        ChipState(id = id, WEEK.SATURDAY),
        ChipState(id = id, WEEK.SUNDAY)
    )

)

data class ChipState(
    val id: Int, val dayOfWeek: WEEK, val isActive: Boolean = false, val isVisible: Boolean = true
)

enum class WEEK(val id: Int, val label: String) {
    MONDAY(id = 0, label = "M"),
    TUESDAY(id = 1, label = "T"),
    WEDNESDAY(id = 2, label = "W"),
    THURSDAY(id = 3, label = "Th"),
    FRIDAY(id = 4, label = "F"),
    SATURDAY(id = 5, "Sa"),
    SUNDAY(id = 6, label = "S")
}