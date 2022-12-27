package dk.kriaactividade.mealngram.data.repository

import dk.kriaactividade.mealngram.data.domain.RecipeDTO
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun selectedRecipes(selectableRecipes:List<SelectableRecipe>)
    suspend fun getSelectedRecipes() : Flow<DataState<List<SelectableRecipe>>>
    suspend fun getAllRecipes() : Flow<DataState<List<RecipeEntity>>>
}