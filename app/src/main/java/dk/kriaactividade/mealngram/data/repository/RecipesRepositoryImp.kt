package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.LoadingState
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.recipeList.SelectedChipState
import dk.kriaactividade.mealngram.presentation.recipeList.daysUntilTheEndOfWeek
import dk.kriaactividade.mealngram.presentation.recipeList.toWeek
import dk.kriaactividade.mealngram.presentation.recipesSelected.RecipesSelectedItem
import dk.kriaactividade.mealngram.presentation.recipesSelected.toRecipeSelectedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RecipesRepositoryImp @Inject constructor(private val database: FirebaseFirestore) :
    RecipesRepository {

//    override suspend fun selectedRecipes(details: List<RecipesDetails>) {
//        details.map { detail ->
//            database.collection("selected-recipes").add(
//                RecipesDetails(
//                    id = detail.id,
//                    name = detail.name,
//                    description = detail.description,
//                    image = detail.image,
//                    day = detail.day,
//                    dayOfWeek = detail.dayOfWeek,
//                    ingredients = detail.ingredients
//                )
//            )
//        }
//    }
//
//    override suspend fun getSelectedRecipes(): Flow<DataState<List<RecipesSelectedItem>>> = flow {
//        emit(DataState.Loading(loadingState = LoadingState.Loading))
//
//        val snapshot = database.collection(SELECTED_RECIPES).get().await()
//        val recipes = snapshot.toObjects(Recipe::class.java)
//
//        val recipesSelectedItem = recipes.toRecipeSelectedItem()
//        emit(DataState.Data(data = recipesSelectedItem))
//    }
//


    override suspend fun getAllRecipes(): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.Loading(loadingState = LoadingState.Loading))

        val snapshot = database.collection(RECIPE).get().await()
        val recipes = snapshot.toObjects(Recipe::class.java)


        emit(DataState.Data(data = recipes))
    }

}