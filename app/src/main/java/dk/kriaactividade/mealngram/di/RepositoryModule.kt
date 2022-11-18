package dk.kriaactividade.mealngram.di

import android.content.Context
import dk.kriaactividade.mealngram.database.room.RoomRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.kriaactividade.mealngram.data.repository.RecipesRepositoryImp
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.RecipeDAO
import dk.kriaactividade.mealngram.database.RecipeDataBase

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesRecipesRepository(database:FirebaseFirestore): RecipesRepository {
        return RecipesRepositoryImp(database)
    }

    @Provides
    fun providesRecipeDAO(@ApplicationContext appContext: Context):RecipeDAO{
        return RecipeDataBase.getDatabase(appContext).recipeDAO()
    }

    @Provides
    fun providesRepository(recipeDAO: RecipeDAO): RoomRepository {
        return RoomRepository(recipeDAO)
    }

}