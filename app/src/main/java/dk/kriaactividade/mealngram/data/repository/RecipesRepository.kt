package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.recipesSelected.RecipesSelectedItem
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    //suspend fun selectedRecipes(details:List<RecipesDetails>)
    //suspend fun getSelectedRecipes() : Flow<DataState<List<RecipesSelectedItem>>>
    suspend fun getAllRecipes() : Flow<DataState<List<Recipe>>>
}