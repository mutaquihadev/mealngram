package dk.kriaactividade.mealngram.entities.domain.chip

import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK
import java.util.*

data class SelectedChip(
    val date: Date,
    val weekDay: WEEK,
    var recipeId: Int? = null,
    var isChecked: Boolean = false
)

fun List<SelectedChip>.toSelectedChipStates(recipeItemId: Int): List<SelectedChipState> {
    return this.map { selectedChip ->

        val chipState = if (selectedChip.recipeId == null) {
            ChipStateOptions.NOT_SELECTED
        } else if (selectedChip.recipeId != recipeItemId) {
            ChipStateOptions.DISABLED
        } else {
            if (selectedChip.isChecked) {
                ChipStateOptions.SELECTED
            } else {
                ChipStateOptions.NOT_SELECTED
            }
        }

        when (chipState) {
            ChipStateOptions.NOT_SELECTED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = true,
                    isChecked = false
                )
            }
            ChipStateOptions.SELECTED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = true,
                    isChecked = true
                )
            }
            ChipStateOptions.DISABLED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = false,
                    isChecked = false
                )
            }
        }
    }
}