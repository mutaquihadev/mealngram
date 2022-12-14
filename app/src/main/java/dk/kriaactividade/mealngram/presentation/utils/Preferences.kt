package dk.kriaactividade.mealngram.presentation.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dk.kriaactividade.mealngram.application.RecipesApplication


object Preferences {
    private var prefs: SharedPreferences = RecipesApplication().getContext().getSharedPreferences(
        "settings", MODE_PRIVATE
    )
    private var listRecipeID: MutableSet<String> = hashSetOf()

    fun getRecipeListId(): MutableList<String> {
        return ArrayList(listRecipeID)
    }

    fun setRecipeListId(recipeId: MutableList<Int>) {
        listRecipeID.addAll(listOf(recipeId.toString()))
        prefs.edit().putStringSet("ListInt", listRecipeID).apply()
    }

    fun removeRecipeListId() {
        prefs.edit().remove("ListInt").clear().apply()
    }

}

