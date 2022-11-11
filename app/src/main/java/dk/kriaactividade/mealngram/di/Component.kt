package dk.kriaactividade.mealngram.di

import dk.kriaactividade.mealngram.MainActivity
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = [RepositoryModule::class])
interface Component {

    fun inject(mainActivity: MainActivity)
}