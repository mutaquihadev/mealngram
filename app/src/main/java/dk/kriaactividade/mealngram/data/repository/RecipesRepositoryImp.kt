package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.LoadingState
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.recipeList.toRecipeItems
import dk.kriaactividade.mealngram.presentation.recipesSelected.RecipesSelectedItem
import dk.kriaactividade.mealngram.presentation.recipesSelected.toRecipeSelectedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipesRepositoryImp @Inject constructor(private val database: FirebaseFirestore,private val auth: FirebaseAuth) :
    RecipesRepository {

    override suspend fun selectedRecipes(details: List<DetailsRecipes>) {
        details.map { detail ->
            database.collection("selected-recipes").add(
                DetailsRecipes(
                    id = detail.id,
                    name = detail.name,
                    description = detail.description,
                    image = detail.image,
                    day = detail.day,
                    dayOfWeek = detail.dayOfWeek,
                    ingredients = detail.ingredients
                )
            )
        }
    }

    override suspend fun getSelectedRecipes(): Flow<DataState<List<RecipesSelectedItem>>> = flow {
        emit(DataState.Loading(loadingState = LoadingState.Loading))

        val snapshot = database.collection(SELECTED_RECIPES).get().await()
        val recipes = snapshot.toObjects(Recipe::class.java)

        val recipesSelectedItem = recipes.toRecipeSelectedItem()
        emit(DataState.Data(data = recipesSelectedItem))
    }

    override suspend fun getLogin(
        activity: Activity,
        email: String,
        password: String,
        onLogged: (Boolean,String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(
            email, password
        )
            .addOnCompleteListener(activity) { task ->
                onLogged(task.isSuccessful,task.exception?.message)
            }
    }

    override suspend fun getIsLogged(onLogged: (Boolean) -> Unit) {
        onLogged(auth.currentUser != null)
    }

    override suspend fun registerUser(
        activity: Activity,
        email: String,
        password: String,
        onRegister: (Boolean,String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            email,
            password
        )
            .addOnCompleteListener(activity) { task ->
                onRegister(task.isSuccessful,task.exception?.message)
            }
    }

    override suspend fun getAllRecipes(): Flow<DataState<List<RecipeItem>>> = flow {
        emit(DataState.Loading(loadingState = LoadingState.Loading))

        val snapshot = database.collection(RECIPE).get().await()
        val recipes = snapshot.toObjects(Recipe::class.java)

        val recipeItems = recipes.toRecipeItems()

        emit(DataState.Data(data = recipeItems))
    }
}