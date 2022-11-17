package dk.kriaactividade.mealngram.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
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

    override suspend fun selectedRecipes(details: List<DetailsRecipes>) {
        details.map { detail ->
            database.collection("selected-recipes").add(
                DetailsRecipes(
                    id = detail.id,
                    name = detail.name,
                    description = detail.description,
                    image = detail.image,
                    day = detail.day,
                    dayOfWeek = detail.dayOfWeek
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

}