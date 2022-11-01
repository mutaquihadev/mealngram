package dk.kriaactividade.mealngram.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.kriaactividade.mealngram.data.domain.RecipesUseCase
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.repository.service.RecipesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@dagger.Module
class Module {
    @Provides
    fun providesRecipesRepository(recipesService: RecipesService): RecipesRepository {
        return RecipesUseCase(recipesService)
    }

    @Provides
    fun providesRetrofit(): RecipesService {
        return Retrofit.Builder()
            .baseUrl("https://5eef-89-23-224-153.eu.ngrok.io")
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            ).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipesService::class.java)
    }
}