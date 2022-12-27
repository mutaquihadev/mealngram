package dk.kriaactividade.mealngram.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.kriaactividade.mealngram.data.repository.AuthRepository
import dk.kriaactividade.mealngram.data.repository.AuthRepositoryImp
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.data.repository.RecipesRepositoryImp
import dk.kriaactividade.mealngram.database.RecipeDAO
import dk.kriaactividade.mealngram.database.RecipeDataBase
import dk.kriaactividade.mealngram.database.RecipeWeekDAO

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesRecipesRepository(
        database: FirebaseFirestore,
        recipeDAO: RecipeDAO
    ): RecipesRepository {
        return RecipesRepositoryImp(database, recipeDAO)
    }

    @Provides
    fun providesAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImp(auth)
    }

    @Provides
    fun providesRecipeDAO(@ApplicationContext appContext: Context): RecipeDAO {
        return RecipeDataBase.getDatabase(appContext).recipeDAO()
    }

    @Provides
    fun providesRecipeWeekDAO(@ApplicationContext appContext: Context): RecipeWeekDAO {
        return RecipeDataBase.getDatabase(appContext).recipeWeekDAO()
    }
}