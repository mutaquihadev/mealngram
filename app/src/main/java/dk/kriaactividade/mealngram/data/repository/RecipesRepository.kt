package dk.kriaactividade.mealngram.data.repository

import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse

interface RecipesRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun myRecipes(): MutableList<RecipesResponse>
}