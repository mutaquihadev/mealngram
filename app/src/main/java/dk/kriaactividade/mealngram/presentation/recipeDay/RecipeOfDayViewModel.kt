package dk.kriaactividade.mealngram.presentation.recipeDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.presentation.utils.Util.getCurrentDate
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
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

}