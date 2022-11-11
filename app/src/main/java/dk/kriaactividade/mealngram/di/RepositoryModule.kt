package dk.kriaactividade.mealngram.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.kriaactividade.mealngram.data.repository.RecipesRepositoryImp
import dk.kriaactividade.mealngram.data.repository.RecipesRepository

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesRecipesRepository(database:FirebaseFirestore): RecipesRepository {
        return RecipesRepositoryImp(database)
    }

}