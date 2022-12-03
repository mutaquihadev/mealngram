package dk.kriaactividade.mealngram.presentation.recipeList

import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.WEEK
import java.util.*


data class RecipeItem(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val isSelectionMode: Boolean = false,
) {
    private val calendar = Calendar.getInstance()
    val selectedDays: List<SelectedChipState>
        get() = calendar.daysUntilTheEndOfWeek().map { SelectedChipState(id = id, date = it, week = it.toWeek()) }
}

data class SelectedChipState(
    val id: Int, val date: Date, val isActive: Boolean = false, val week : WEEK
)

fun Calendar.daysUntilTheEndOfWeek(): List<Date> {
    val dates = mutableListOf<Date>()
    do {
        dates.add(this.time)
        this.add(Calendar.DAY_OF_MONTH, 1)
    } while (this.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)

    return dates.toList()
}

fun List<Recipe>.toRecipeItems(): List<RecipeItem> {
    return this.map { recipe ->
        RecipeItem(
            id = recipe.id,
            name = recipe.name,
            image = recipe.image,
            description = recipe.description,
            ingredients = recipe.ingredients,
        )
    }
}

fun Date.toWeek(): WEEK {
    val calendar = Calendar.getInstance()
    calendar.time = this

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> WEEK.MONDAY
        Calendar.TUESDAY -> WEEK.TUESDAY
        Calendar.WEDNESDAY -> WEEK.WEDNESDAY
        Calendar.THURSDAY -> WEEK.THURSDAY
        Calendar.FRIDAY -> WEEK.FRIDAY
        Calendar.SATURDAY -> WEEK.SATURDAY
        Calendar.SUNDAY -> WEEK.SUNDAY
        else -> {
            WEEK.MONDAY
        }
    }
}