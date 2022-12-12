package dk.kriaactividade.mealngram.database.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.RecipeDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class RoomRepository @Inject constructor(private val recipeDAO: RecipeDAO) : ViewModel() {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun allRecipes(): List<RecipeRoomItem> {
       return recipeDAO.getAllRecipes()
    }

    fun insert(recipeDetails: RecipeRoomItem) = viewModelScope.launch {
        recipeDAO.insertRecipe(recipeDetails)
    }

    fun insertList(listRecipe: List<RecipeRoomItem>) = viewModelScope.launch {
        recipeDAO.insertList(listRecipe)
    }

//     fun remove(recipeId: Int) = viewModelScope.launch {
//        recipeDAO.removeRecipe(recipeId)
//    }

    fun getRecipe(recipeId: Int) = viewModelScope.launch {
        recipeDAO.geRecipe(recipeId)
    }

}