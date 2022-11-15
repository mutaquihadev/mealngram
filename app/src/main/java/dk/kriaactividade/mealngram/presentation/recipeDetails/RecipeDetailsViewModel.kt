package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository) : ViewModel() {
    val myRecipes: LiveData<List<DetailsRecipes>>
        get() = _myRecipes
    private val _myRecipes = MutableLiveData<List<DetailsRecipes>>()


    fun setDetailsList(detailsRecipes: List<DetailsRecipes>){
        viewModelScope.launch {
            _myRecipes.postValue(detailsRecipes)
        }
    }
}