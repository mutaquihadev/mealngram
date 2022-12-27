package dk.kriaactividade.mealngram.data.repository

import android.app.Activity

interface AuthRepository {
    suspend fun getLogin(activity: Activity, email:String, password:String, onLogged:(Boolean, String?)->Unit)
    suspend fun getIsLogged(onLogged:(Boolean)->Unit)
    suspend fun registerUser(activity: Activity,email:String,password:String,onRegister:(Boolean,String?)->Unit)
}