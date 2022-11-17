package dk.kriaactividade.mealngram.database.room

import androidx.annotation.WorkerThread
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.RecipeDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val recipeDAO: RecipeDAO) {
    val allPerson: Flow<MutableList<Recipe>> = recipeDAO.getAllRecipes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movieDetails: Recipe) {
        recipeDAO.insertRecipe(movieDetails)
    }

    suspend fun remove(movieId: Int) {
        recipeDAO.removeRecipe(movieId)
    }

}