package dk.kriaactividade.mealngram.data.repository

import dk.kriaactividade.mealngram.repository.remote.RecipesResponse

interface RecipesRepository {
    suspend fun getRecipes(): MutableList<RecipesResponse>
}