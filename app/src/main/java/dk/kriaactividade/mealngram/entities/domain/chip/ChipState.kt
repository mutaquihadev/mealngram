package dk.kriaactividade.mealngram.entities.domain.chip

import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK

data class ChipState(
    val id: Int,
    val dayOfWeek: WEEK,
    val isActive: Boolean = false,
    val isSelectable: Boolean = true
)
