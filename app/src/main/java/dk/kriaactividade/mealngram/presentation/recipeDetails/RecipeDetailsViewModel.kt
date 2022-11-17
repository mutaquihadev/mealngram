package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {

    fun setDetailsList(detailsRecipes: List<DetailsRecipes>) {
        viewModelScope.launch {
            repository.selectedRecipes(detailsRecipes)
        }
    }
}