package dk.kriaactividade.mealngram.repository.service

import dk.kriaactividade.mealngram.repository.remote.RecipeDTO
import retrofit2.http.GET

interface RecipesService {

    @GET("/recipes")
    suspend fun getRecipes():List<RecipeDTO>

    @GET("my_recipes")
    suspend fun myRecipes():List<RecipeDTO>

}