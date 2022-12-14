package dk.kriaactividade.mealngram.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.database.room.RecipeRoomItem
import dk.kriaactividade.mealngram.database.room.RoomRepository
import dk.kriaactividade.mealngram.presentation.favorite.selectFavorite.SelectFavoriteItem
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListUiData
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListUiState
import dk.kriaactividade.mealngram.presentation.utils.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        viewModelScope.launch {
            Preferences.getRecipeListId()?.map {
              val stringFormat = it.replace("[", "").replace(",", "").replace("]", "");
                roomRepository.getRecipe(stringFormat.toInt()).map { recipeItem ->
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