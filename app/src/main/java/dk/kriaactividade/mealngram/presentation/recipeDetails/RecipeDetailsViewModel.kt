package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.ViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {


//    suspend fun getDetailsList(weekNumber:Int):List<RecipeRoomWeekItem> {
//       return repository.geRecipeWeek(weekNumber)
//    }
}