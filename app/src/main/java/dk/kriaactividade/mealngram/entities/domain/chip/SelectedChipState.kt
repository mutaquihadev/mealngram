package dk.kriaactividade.mealngram.entities.domain.chip

import java.util.*

data class SelectedChipState(
    val id: Int,
    val date: Date,
    val isChecked: Boolean = false,
    val isSelectable: Boolean = true,
)