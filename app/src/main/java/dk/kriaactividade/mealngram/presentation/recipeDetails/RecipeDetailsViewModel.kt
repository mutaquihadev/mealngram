package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository) : ViewModel() {
    val myRecipes: LiveData<List<RecipeDTO>>
        get() = _myRecipes
    private val _myRecipes = MutableLiveData<List<RecipeDTO>>()

    init {
        viewModelScope.launch {
            val recipe = repository.myRecipes()
            _myRecipes.postValue(recipe)
        }
    }
}