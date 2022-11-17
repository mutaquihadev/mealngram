package dk.kriaactividade.mealngram.presentation.recipesSelected

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class RecipesSelectedViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {

    val recipesSelected: LiveData<List<RecipesSelected>>
        get() = _recipesSelected
    private val _recipesSelected = MutableLiveData<List<RecipesSelected>>()

    init {
        viewModelScope.launch {
            repository.getSelectedRecipes {
                _recipesSelected.postValue(it)
            }
        }
    }
}