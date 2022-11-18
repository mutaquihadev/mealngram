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

    val allPerson: LiveData<MutableList<Recipe>> = repository.allPerson.asLiveData()

    fun insert(movieDetails: Recipe) = viewModelScope.launch {
        repository.insert(movieDetails)
    }

    fun remove(movieId: Int) = viewModelScope.launch {
        repository.remove(movieId)
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