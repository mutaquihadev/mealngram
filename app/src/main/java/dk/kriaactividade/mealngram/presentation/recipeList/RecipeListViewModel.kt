package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {

    val recipes: LiveData<List<Recipe>>
        get() = _recipes
    private val _recipes = MutableLiveData<List<Recipe>>()

    val valueProgress: LiveData<Int>
        get() = _valueProgress
    private val _valueProgress = MutableLiveData<Int>()


    val isEditMode: LiveData<Boolean>
        get() = _isEditMode
    private val _isEditMode = MutableLiveData<Boolean>(false)

    init {
        viewModelScope.launch {
            val recipe = repository.getRecipes()
            _recipes.postValue(recipe)
        }
    }

    fun getValueProgress(value: Int) {
        _valueProgress.postValue(value)
    }


    fun updateEditMode() {
        isEditMode.value?.let { currentEditMode ->
            val updatedEditMode = !currentEditMode
            _isEditMode.postValue(updatedEditMode)

            updateRecipes(updatedEditMode)
        }
    }

    private fun updateRecipes(updatedEditMode: Boolean) {
        val recipes = _recipes.value?.map { recipe ->
            Recipe(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients,
                images = recipe.images,
                video = recipe.video,
                mainImage = recipe.mainImage,
                isSelectionMode = updatedEditMode
            )
        } ?: emptyList()

        _recipes.postValue(recipes)
    }

}