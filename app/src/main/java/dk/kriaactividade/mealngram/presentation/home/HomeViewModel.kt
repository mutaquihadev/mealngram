package dk.kriaactividade.mealngram.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: RecipesRepository) : ViewModel() {
    val recipes: LiveData<MutableList<RecipesResponse>>
    get() = _recipes
    private val _recipes = MutableLiveData<MutableList<RecipesResponse>>()

    fun getRecipesList(){
        viewModelScope.launch {
            val recipe = repository.getRecipes()
            _recipes.postValue(recipe)
        }
    }
}