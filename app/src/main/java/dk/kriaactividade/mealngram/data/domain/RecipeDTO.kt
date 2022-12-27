package dk.kriaactividade.mealngram.data.domain

data class RecipeDTO(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = listOf(),
    val image: String = "",
    val video: String? = "",
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
    val id: Int,
    val dayOfWeek: WEEK,
    val isActive: Boolean = false,
    val isSelectable: Boolean = true
)

enum class WEEK(val id: Int, val label: String) {
    MONDAY(id = 0, label = "Seg"),
    TUESDAY(id = 1, label = "Ter"),
    WEDNESDAY(id = 2, label = "Qua"),
    THURSDAY(id = 3, label = "Qui"),
    FRIDAY(id = 4, label = "Sex"),
    SATURDAY(id = 5, "Sab"),
    SUNDAY(id = 6, label = "Dom")
}