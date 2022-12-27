package dk.kriaactividade.mealngram.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dk.kriaactividade.mealngram.data.domain.RecipeDTO
import dk.kriaactividade.mealngram.database.RecipeDAO
import dk.kriaactividade.mealngram.database.SelectableRecipeDAO
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.LoadingState
import dk.kriaactividade.mealngram.presentation.utils.isSameDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RecipesRepositoryImp @Inject constructor(
    private val database: FirebaseFirestore,
    private val recipeDAO: RecipeDAO,
    private val selectableRecipeDAO: SelectableRecipeDAO
) :
    RecipesRepository {

    override suspend fun selectedRecipes(selectableRecipes: List<SelectableRecipe>) {

    }

    override suspend fun getSelectedRecipes(): Flow<DataState<List<SelectableRecipe>>> = flow {

    }


    override suspend fun getAllRecipes(): Flow<DataState<List<RecipeEntity>>> = flow {
        emit(DataState.Loading(loadingState = LoadingState.Loading))
        val today = Calendar.getInstance().time
        val cachedRecipeEntities = recipeDAO.getAllRecipes()
        val isCacheClean = cachedRecipeEntities.isNotEmpty() && cachedRecipeEntities.first().dateInserted.isSameDay(today)

        if (isCacheClean) {
            emit(DataState.Data(data = cachedRecipeEntities))
            return@flow
        }

        val snapshot = database.collection(RECIPE).get().await()
        val recipeDTOs = snapshot.toObjects(RecipeDTO::class.java)
        val recipeEntities: List<RecipeEntity> = recipeDTOs.map { recipeDTO ->
            RecipeEntity(
                id = recipeDTO.id,
                name = recipeDTO.name,
                image = recipeDTO.image,
                description = recipeDTO.description,
                ingredients = recipeDTO.ingredients,
                dateInserted = Calendar.getInstance().time
            )
        }
        recipeDAO.insertList(recipeEntities)
        val recentlyCachedRecipeEntities = recipeDAO.getAllRecipes()
        emit(DataState.Data(data = recentlyCachedRecipeEntities))
    }
}