package dk.kriaactividade.mealngram.entities.domain.recipe

import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChipState

data class RecipeItem(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val selectedDays: List<SelectedChipState>
)