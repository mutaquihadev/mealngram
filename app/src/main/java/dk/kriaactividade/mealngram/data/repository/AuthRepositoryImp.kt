package dk.kriaactividade.mealngram.data.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(private val auth:FirebaseAuth): AuthRepository{
        override suspend fun login(
            activity: Activity,
            email: String,
            password: String,
            onLogged: (Boolean,String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(
            email, password
        )
            .addOnCompleteListener(activity) { task ->
                onLogged(task.isSuccessful,task.exception?.message)
            }
    }

    override suspend fun isLogged() = auth.currentUser != null


    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun registerUser(
        activity: Activity,
        email: String,
        password: String,
        onRegister: (Boolean,String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            email,
            password
        )
            .addOnCompleteListener(activity) { task ->
                onRegister(task.isSuccessful,task.exception?.message)
            }
    }
}