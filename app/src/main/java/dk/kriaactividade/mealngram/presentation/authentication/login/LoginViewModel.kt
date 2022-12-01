package dk.kriaactividade.mealngram.presentation.authentication.login

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dk.kriaactividade.mealngram.MainActivity
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth

    val loginSuccess: LiveData<Boolean>
    get() = _loginSuccess
    private val _loginSuccess = MutableLiveData<Boolean>()

    val userLogged: LiveData<Boolean>
        get() = _userLogged
    private val _userLogged = MutableLiveData<Boolean>()

    fun login(activity: LoginActivity, email:String, password:String){
        auth.signInWithEmailAndPassword(
        email, password
        )
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    _loginSuccess.postValue(true)
                } else {
                   _loginSuccess.postValue(false)
                }
            }
    }

    fun verifyUserLogin(){
        val currentUser = auth.currentUser
        if(currentUser != null){
           _userLogged.postValue(true)
        }else{
            _userLogged.postValue(false)
        }
    }
}