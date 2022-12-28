package dk.kriaactividade.mealngram.data.repository

import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun saveSelectedRecipes(selectedList:List<SelectableRecipe>)
    suspend fun getAllSelectedRecipes(): Flow<DataState<List<SelectableRecipe>>>
    suspend fun getRecipeOfDay(weekNumber: Int): Flow<DataState<SelectableRecipe>>
    suspend fun selectedRecipes(weekNumber:Int): Flow<DataState<List<SelectableRecipe>>>
    suspend fun getSelectedRecipes() : Flow<DataState<List<SelectableRecipe>>>
    suspend fun getAllRecipes() : Flow<DataState<List<RecipeEntity>>>
}