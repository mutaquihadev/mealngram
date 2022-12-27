package dk.kriaactividade.mealngram.data.repository

import dk.kriaactividade.mealngram.data.domain.RecipeDTO
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    //suspend fun selectedRecipes(details:List<RecipesDetails>)
    //suspend fun getSelectedRecipes() : Flow<DataState<List<RecipesSelectedItem>>>
    suspend fun getAllRecipes() : Flow<DataState<List<RecipeEntity>>>
}