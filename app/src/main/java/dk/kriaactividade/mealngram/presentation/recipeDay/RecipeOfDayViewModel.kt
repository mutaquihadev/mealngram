package dk.kriaactividade.mealngram.presentation.recipeDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem
import java.util.*
import javax.inject.Inject

class RecipeOfDayViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {

    val currentRecipe: LiveData<RecipeRoomWeekItem>
    get() = _currentRecipe
    private val _currentRecipe = MutableLiveData<RecipeRoomWeekItem>()

//    init {
//        viewModelScope.launch {
//            repository.getAllRecipesWeek().map {
//                if (compareDay(it.dateWeek) == getCurrentDate()){
//                    val recipe = RecipeRoomWeekItem(
//                        id = it.id,
//                        name = it.name,
//                        description = it.description,
//                        ingredients = it.ingredients,
//                        dateWeek = it.dateWeek,
//                        image = it.image,
//                        weekNumber = it.weekNumber
//                    )
//                    _currentRecipe.postValue(recipe)
//                }
//            }
//        }
//    }

    private fun compareDay(dateSave: Date): Int {
        val date = Date(dateSave.time)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return day + month + year
    }

    private fun getCurrentDate():Int{
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return day + month + year
    }
}