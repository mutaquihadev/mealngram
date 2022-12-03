package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO

interface RecipesRepository {
    suspend fun getRecipes(onRecipesRetrieved: (List<Recipe>) -> Unit)
    suspend fun selectedRecipes(details:List<DetailsRecipes>)
    suspend fun getSelectedRecipes(onRecipesSelected:(List<RecipesSelected>) -> Unit)
    suspend fun getLogin(activity: Activity,email:String,password:String,onLogged:(Boolean,String?)->Unit)
    suspend fun getIsLogged(onLogged:(Boolean)->Unit)
    suspend fun registerUser(activity: Activity,email:String,password:String,onRegister:(Boolean,String?)->Unit)
}