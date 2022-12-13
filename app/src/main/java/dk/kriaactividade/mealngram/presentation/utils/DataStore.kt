package dk.kriaactividade.mealngram.presentation.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dk.kriaactividade.mealngram.application.RecipesApplication
import dk.kriaactividade.mealngram.application.RecipesApplication.Companion.getApplicationContext
import dk.kriaactividade.mealngram.presentation.favorite.selectFavorite.DataStoreSaveId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recipes")

    private val RECIPE_ID = intPreferencesKey("recipeId")

    suspend fun saveRecipeId(recipeId: DataStoreSaveId) {
        getApplicationContext.dataStore.edit { preferences ->
            preferences[RECIPE_ID] = recipeId.id
        }
    }

    fun getUserFromPreferencesStore(): Flow<DataStoreSaveId> =
        getApplicationContext.dataStore.data.map { preferences ->
            DataStoreSaveId(
                id = preferences[RECIPE_ID] ?: 0,
            )
        }
}

