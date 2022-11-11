package dk.kriaactividade.mealngram.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO
import javax.inject.Inject

class RecipesRepositoryImp @Inject constructor(private val database: FirebaseFirestore) :
    RecipesRepository {


    override suspend fun getRecipes(onRecipesRetrieved: (List<Recipe>) -> Unit) {
        database.collection("recipes").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val recipes = task.result.toObjects(Recipe::class.java)
                onRecipesRetrieved(recipes)
            }
        }
    }

    override suspend fun myRecipes(): List<RecipeDTO> {
        return listOf()
    }

}