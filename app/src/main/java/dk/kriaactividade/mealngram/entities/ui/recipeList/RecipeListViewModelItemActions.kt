package dk.kriaactividade.mealngram.entities.ui.recipeList

import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChipState
import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK
import java.util.*

interface RecipeListViewModelItemActions {
    fun onSelectableDaySelected(selectableDay : SelectedChipState)
}