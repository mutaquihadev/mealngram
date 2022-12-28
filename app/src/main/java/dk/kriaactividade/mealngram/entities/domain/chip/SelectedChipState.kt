package dk.kriaactividade.mealngram.entities.domain.chip

import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK
import java.util.*

data class SelectedChipState(
    val id: Int,
    val date: Date,
    val isChecked: Boolean = false,
    val isSelectable: Boolean = true,
    val week: WEEK
)