package dk.kriaactividade.mealngram.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.database.room.RecipeRoomItem
import dk.kriaactividade.mealngram.database.room.RoomRepository
import dk.kriaactividade.mealngram.presentation.utils.Preferences
import dk.kriaactividade.mealngram.presentation.utils.convertStringForInt
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class FavoriteRecipesViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {

    private val listFavorites = mutableListOf<RecipeRoomItem>()

    val getFavorite: LiveData<MutableList<RecipeRoomItem>>
        get() = _getFavorite
    private val _getFavorite = MutableLiveData<MutableList<RecipeRoomItem>>()


    fun handleGetAllRecipes() {
        if (listFavorites.isNotEmpty()){
            listFavorites.clear()
        }
        viewModelScope.launch {
            Preferences.getRecipeListId()?.map {
                roomRepository.getRecipe(it.convertStringForInt()).map { recipeItem ->
                    val selectFavoriteItem = RecipeRoomItem(
                        id = recipeItem.id,
                        name = recipeItem.name,
                        image = recipeItem.image,
                        description = recipeItem.description,
                        ingredients = recipeItem.ingredients,
                        dateInserted = getCurrentDate()
                    )
                    listFavorites.add(selectFavoriteItem)
                }
                _getFavorite.postValue(listFavorites)
            }
        }
    }

    private fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }
}