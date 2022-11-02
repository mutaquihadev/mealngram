package dk.kriaactividade.mealngram.repository.service

import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import retrofit2.http.GET

interface RecipesService {

    @GET("/recipes")
    suspend fun getRecipes():MutableList<RecipesResponse>

    @GET("my_recipes")
    suspend fun myRecipes():MutableList<RecipesResponse>
}