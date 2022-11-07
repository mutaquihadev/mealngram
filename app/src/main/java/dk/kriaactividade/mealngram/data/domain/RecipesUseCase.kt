package dk.kriaactividade.mealngram.data.domain

import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO
import dk.kriaactividade.mealngram.repository.service.RecipesService
import javax.inject.Inject

class RecipesUseCase @Inject constructor(private val service: RecipesService) : RecipesRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return service.getRecipes().map {
            Recipe(
                id = it.id,
                name = it.name,
                description = it.description,
                ingredients = it.ingredients,
                images = it.imagesUrl,
                video = it.videoUrl,
                mainImage = it.imagesUrl.firstOrNull()
            )
        }
    }

    override suspend fun myRecipes(): List<RecipeDTO> {
        return service.myRecipes()
    }

}