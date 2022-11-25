package dk.kriaactividade.mealngram.database.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.Recipe
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {

    val allRecipes: LiveData<MutableList<Recipe>> = repository.allRecipes.asLiveData()

    fun insert(recipes: Recipe) = viewModelScope.launch {
        repository.insert(recipes)
    }

    fun remove(recipeId: Int) = viewModelScope.launch {
        repository.remove(recipeId)
    }

    fun insertList(listRecipe: List<Recipe>) {
        listRecipe.map { recipe ->
            insert(
                Recipe(
                    id = recipe.id,
                    name = recipe.name,
                    description = recipe.description,
                    ingredients = recipe.ingredients,
                    image = recipe.image,
                    video = recipe.video,
                    dayOfWeekSelectedPair = recipe.dayOfWeekSelectedPair
                )
            )
        }
    }
}