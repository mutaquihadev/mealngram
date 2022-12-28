package dk.kriaactividade.mealngram.entities.dto

import dk.kriaactividade.mealngram.entities.domain.chip.ChipState
import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK

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
