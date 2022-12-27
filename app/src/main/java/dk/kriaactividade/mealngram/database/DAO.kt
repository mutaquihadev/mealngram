package dk.kriaactividade.mealngram.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem

@Dao
interface RecipeDAO {
    @Query("SELECT * from table_recipe ORDER BY id")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM table_recipe WHERE id = :recipeId")
    suspend fun geRecipe(recipeId: Int): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeDetails: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listRecipe: List<RecipeEntity>)

    @Query("DELETE from table_recipe")
    suspend fun deleteAllRecipes()

}

@Dao
interface RecipeWeekDAO {
    @Query("SELECT * from table_recipe_week ORDER BY roomId")
    suspend fun getAllRecipesWeek(): List<RecipeRoomWeekItem>

    @Query("SELECT * FROM table_recipe_week WHERE weekNumber = :weekNumber")
    suspend fun geRecipeWeek(weekNumber: Int): List<RecipeRoomWeekItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeWeek(recipeDetails: RecipeRoomWeekItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListWeek(listRecipe: List<RecipeRoomWeekItem>)

    @Query("DELETE from table_recipe_week")
    suspend fun deleteAllRecipesWeek()

}