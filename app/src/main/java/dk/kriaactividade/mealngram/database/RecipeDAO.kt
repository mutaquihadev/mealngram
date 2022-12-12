package dk.kriaactividade.mealngram.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.room.RecipeRoomItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {
    @Query("SELECT * from table_recipe ORDER BY myId")
    suspend fun getAllRecipes(): List<RecipeRoomItem>

    @Query("SELECT * FROM table_recipe WHERE recipe = :recipeId")
    suspend fun geRecipe(recipeId: Int): List<RecipeRoomItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(recipeDetails: RecipeRoomItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(listRecipe: List<RecipeRoomItem>)

//    @Query("DELETE from table_recipe WHERE recipe = :recipeId")
//    suspend fun removeRecipe(recipeId: Int)

}