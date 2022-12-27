package dk.kriaactividade.mealngram.database.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import dk.kriaactividade.mealngram.database.RecipeDAO
import dk.kriaactividade.mealngram.database.RecipeWeekDAO
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

     fun deleteAllRecipes() = viewModelScope.launch {
        recipeDAO.deleteAllRecipes()
    }

    suspend fun getRecipe(recipeId: Int) : List<RecipeRoomItem>{
        return recipeDAO.geRecipe(recipeId)
    }
}

@ViewModelScoped
class RecipeWeekRepository @Inject constructor(private val recipeWeekDAO: RecipeWeekDAO) : ViewModel() {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllRecipesWeek(): List<RecipeRoomWeekItem> {
        return recipeWeekDAO.getAllRecipesWeek()
    }

    fun insertRecipeWeek(recipeDetails: RecipeRoomWeekItem) = viewModelScope.launch {
        recipeWeekDAO.insertRecipeWeek(recipeDetails)
    }

    fun insertListWeek(listRecipe: List<RecipeRoomWeekItem>) = viewModelScope.launch {
        recipeWeekDAO.insertListWeek(listRecipe)
    }

    fun deleteAllRecipesWeek() = viewModelScope.launch {
        recipeWeekDAO.deleteAllRecipesWeek()
    }

    suspend fun geRecipeWeek(weekNumber: Int) : List<RecipeRoomWeekItem>{
        return recipeWeekDAO.geRecipeWeek(weekNumber)
    }
}