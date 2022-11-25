package dk.kriaactividade.mealngram.database.room

import androidx.annotation.WorkerThread
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.RecipeDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val recipeDAO: RecipeDAO) {
    val allRecipes: Flow<MutableList<Recipe>> = recipeDAO.getAllRecipes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(recipe: Recipe) {
        recipeDAO.insertRecipe(recipe)
    }

    suspend fun remove(recipeId: Int) {
        recipeDAO.removeRecipe(recipeId)
    }

}