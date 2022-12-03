package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.LoadingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipesRepositoryImp @Inject constructor(private val database: FirebaseFirestore,private val auth: FirebaseAuth) :
    RecipesRepository {

    override suspend fun getRecipes(onRecipesRetrieved: (List<Recipe>) -> Unit) {
        database.collection("recipes").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val recipes = task.result.toObjects(Recipe::class.java)
                onRecipesRetrieved(recipes)
            }
        }
    }

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

    override suspend fun getSelectedRecipes(onRecipesSelected: (List<RecipesSelected>) -> Unit) {
        database.collection("selected-recipes").get().addOnCompleteListener { task ->
            if (task.isSuccessful){
                val recipesSelected = task.result.toObjects(RecipesSelected::class.java)
                onRecipesSelected(recipesSelected)
            }
        }
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

    override fun getAllRecipes(): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.Loading(loadingState = LoadingState.Loading))

        val snapshot = database.collection(RECIPE).get().await()
        val recipes = snapshot.toObjects(Recipe::class.java)

        emit(DataState.Data(data = recipes))
    }
}