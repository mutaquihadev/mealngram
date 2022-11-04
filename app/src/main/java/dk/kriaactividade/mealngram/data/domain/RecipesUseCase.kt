package dk.kriaactividade.mealngram.data.domain

import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import dk.kriaactividade.mealngram.repository.service.RecipesService
import javax.inject.Inject

class RecipesUseCase @Inject constructor(private val service: RecipesService) : RecipesRepository {

    override suspend fun getRecipes(): MutableList<RecipesResponse> {
        return service.getRecipes()
    }

    override suspend fun myRecipes(): MutableList<RecipesResponse> {
        return service.myRecipes()
    }

}