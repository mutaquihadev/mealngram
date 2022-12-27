package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.recipesSelected.RecipesSelectedItem
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun selectedRecipes(details:List<RecipesDetails>)
    suspend fun getSelectedRecipes() : Flow<DataState<List<RecipesSelectedItem>>>
    suspend fun getLogin(activity: Activity,email:String,password:String,onLogged:(Boolean,String?)->Unit)
    suspend fun getIsLogged(onLogged:(Boolean)->Unit)
    suspend fun registerUser(activity: Activity,email:String,password:String,onRegister:(Boolean,String?)->Unit)
    suspend fun getAllRecipes() : Flow<DataState<List<Recipe>>>
}