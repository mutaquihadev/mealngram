package dk.kriaactividade.mealngram.entities.ui.recipeList

import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK
import java.util.*

interface RecipeListViewModelItemActions {
    fun onDaySelected(recipeId: Int, weekDay: WEEK, date: Date)
}