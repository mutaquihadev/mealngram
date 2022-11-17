package dk.kriaactividade.mealngram.presentation.recipeDay

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RecipeOfDayViewModel @Inject constructor(private val repository: RecipesRepository) : ViewModel() {

    val recipeOfDay : LiveData<RecipesSelected>
    get() = _recipeOfDay
    private val _recipeOfDay = MutableLiveData<RecipesSelected>()

    init {
        viewModelScope.launch {
            repository.getSelectedRecipes { recipesList ->
                recipesList.map { recipe ->
                    RecipesSelected(
                        image = recipe.image,
                        name = recipe.name,
                        day = recipe.day,
                        dayOfWeek = recipe.dayOfWeek,
                        description = recipe.description
                    )
                    if (getCurrentDate() == recipe.day){
                        _recipeOfDay.postValue(recipe)
                    }
                }
            }

        }
    }

    private fun getCurrentDate():String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
       return dateFormat.format(calendar.time.time)
    }
}