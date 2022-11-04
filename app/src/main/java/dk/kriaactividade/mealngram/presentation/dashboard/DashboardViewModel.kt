package dk.kriaactividade.mealngram.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val repository: RecipesRepository) : ViewModel() {
    val myRecipes: LiveData<MutableList<RecipesResponse>>
        get() = _myRecipes
    private val _myRecipes = MutableLiveData<MutableList<RecipesResponse>>()

    fun myRecipes(){
        viewModelScope.launch {
            val recipe = repository.myRecipes()
            _myRecipes.postValue(recipe)
        }
    }
}