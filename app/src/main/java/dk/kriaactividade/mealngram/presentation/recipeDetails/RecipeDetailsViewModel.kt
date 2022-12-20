package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem
import dk.kriaactividade.mealngram.database.room.RecipeWeekRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipeWeekRepository) :
    ViewModel() {


    suspend fun getDetailsList():List<RecipeRoomWeekItem> {
       return repository.allRecipes()
    }
}