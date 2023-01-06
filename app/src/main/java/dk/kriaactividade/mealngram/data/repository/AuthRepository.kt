package dk.kriaactividade.mealngram.data.repository

import android.app.Activity

interface AuthRepository {
    suspend fun login(activity: Activity, email:String, password:String, onLogged:(Boolean, String?)->Unit)
    suspend fun isLogged():Boolean
    suspend fun logout()
    suspend fun registerUser(activity: Activity,email:String,password:String,onRegister:(Boolean,String?)->Unit)
}