package dk.kriaactividade.mealngram.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.room.RecipeRoomItem
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {
    @Query("SELECT * from table_recipe ORDER BY id")
    suspend fun getAllRecipes(): List<RecipeRoomItem>

    @Query("SELECT * FROM table_recipe WHERE id = :recipeId")
    suspend fun geRecipe(recipeId: Int): List<RecipeRoomItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeDetails: RecipeRoomItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listRecipe: List<RecipeRoomItem>)

    @Query("DELETE from table_recipe")
    suspend fun deleteAllRecipes()

}

@Dao
interface RecipeWeekDAO {
    @Query("SELECT * from table_recipe_week ORDER BY id")
    suspend fun getAllRecipesWeek(): List<RecipeRoomWeekItem>

    @Query("SELECT * FROM table_recipe_week WHERE id = :recipeId")
    suspend fun geRecipeWeek(recipeId: Int): List<RecipeRoomWeekItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeWeek(recipeDetails: RecipeRoomWeekItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListWeek(listRecipe: List<RecipeRoomWeekItem>)

    @Query("DELETE from table_recipe_week")
    suspend fun deleteAllRecipesWeek()

}