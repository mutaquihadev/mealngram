package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.ViewModel
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem
import dk.kriaactividade.mealngram.database.room.RecipeWeekRepository
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipeWeekRepository) :
    ViewModel() {


    suspend fun getDetailsList(weekNumber:Int):List<RecipeRoomWeekItem> {
       return repository.geRecipeWeek(weekNumber)
    }
}