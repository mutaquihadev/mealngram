package dk.kriaactividade.mealngram.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import java.util.*
import javax.inject.Inject


class FavoriteRecipesViewModel @Inject constructor(private val roomRepository: RecipesRepository) :
    ViewModel() {

    private val listFavorites = mutableListOf<RecipeEntity>()

    val getFavorite: LiveData<MutableList<RecipeEntity>>
        get() = _getFavorite
    private val _getFavorite = MutableLiveData<MutableList<RecipeEntity>>()

//
//    fun handleGetAllRecipes() {
//        if (listFavorites.isNotEmpty()){
//            listFavorites.clear()
//        }
//        viewModelScope.launch {
//            Preferences.getRecipeListId()?.map {
//                roomRepository.getRecipe(it.convertStringForInt()).map { recipeItem ->
//                    val selectFavoriteItem = RecipeEntity(
//                        id = recipeItem.id,
//                        name = recipeItem.name,
//                        image = recipeItem.image,
//                        description = recipeItem.description,
//                        ingredients = recipeItem.ingredients,
//                        dateInserted = getCurrentDate()
//                    )
//                    listFavorites.add(selectFavoriteItem)
//                }
//                _getFavorite.postValue(listFavorites)
//            }
//        }
//    }

    private fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }
}