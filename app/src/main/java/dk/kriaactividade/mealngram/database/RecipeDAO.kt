package dk.kriaactividade.mealngram.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dk.kriaactividade.mealngram.data.domain.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {
    @Query("SELECT * from table_recipe ORDER BY myId")
    fun getAllRecipes(): Flow<MutableList<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(movieDetails: Recipe)

    @Query("DELETE from table_recipe WHERE recipe = :recipeId")
    suspend fun removeRecipe(recipeId: Int)
}