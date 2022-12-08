package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {

    private val clearSelection:Boolean = false

    fun setDetailsList(detailsRecipes: List<RecipesDetails>) {
        viewModelScope.launch {
            repository.selectedRecipes(detailsRecipes)
        }
    }
}