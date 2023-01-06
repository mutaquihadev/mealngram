package dk.kriaactividade.mealngram.entities.ui.recipeList

import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChipState

interface RecipeListViewModelItemActions {
    fun onSelectableDaySelected(selectableDay : SelectedChipState)
}