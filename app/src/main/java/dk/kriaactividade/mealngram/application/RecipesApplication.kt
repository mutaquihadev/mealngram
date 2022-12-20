package dk.kriaactividade.mealngram.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipesApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        getApplicationContext = applicationContext
    }

    fun getContext(): Context {
        return getApplicationContext
    }
    companion object{
        lateinit var getApplicationContext: Context
    }
}