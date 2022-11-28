package dk.kriaactividade.mealngram.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class ProfileViewModel @Inject constructor():ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth

    val logoutSuccess:LiveData<Boolean>
    get() = _logoutSuccess
    private val _logoutSuccess = MutableLiveData<Boolean>()

    fun logout(){
        auth.signOut()
        verifyIfLogin()
    }

    private fun verifyIfLogin(){
        if (auth.currentUser == null){
            _logoutSuccess.postValue(true)
        }else{
            _logoutSuccess.postValue(false)
        }
    }
}