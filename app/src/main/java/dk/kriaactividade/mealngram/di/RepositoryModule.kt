package dk.kriaactividade.mealngram.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.data.repository.RecipesRepositoryImp
import dk.kriaactividade.mealngram.database.RecipeDAO
import dk.kriaactividade.mealngram.database.RecipeDataBase
import dk.kriaactividade.mealngram.database.RecipeWeekDAO
import dk.kriaactividade.mealngram.database.room.RecipeWeekRepository
import dk.kriaactividade.mealngram.database.room.RoomRepository

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesRecipesRepository(database:FirebaseFirestore,auth: FirebaseAuth): RecipesRepository {
        return RecipesRepositoryImp(database,auth)
    }

    @Provides
    fun providesRecipeDAO(@ApplicationContext appContext: Context):RecipeDAO{
        return RecipeDataBase.getDatabase(appContext).recipeDAO()
    }

    @Provides
    fun providesRecipeWeekDAO(@ApplicationContext appContext: Context):RecipeWeekDAO{
        return RecipeDataBase.getDatabase(appContext).recipeWeekDAO()
    }

    @Provides
    fun providesRecipeWeekRepository(recipeWeekDAO: RecipeWeekDAO): RecipeWeekRepository {
        return RecipeWeekRepository(recipeWeekDAO)
    }

    @Provides
    fun providesRepository(recipeDAO: RecipeDAO): RoomRepository {
        return RoomRepository(recipeDAO)
    }
}