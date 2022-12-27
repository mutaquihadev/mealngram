package dk.kriaactividade.mealngram.presentation.authentication.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {


    val loginSuccess: LiveData<HashMap<Boolean, String?>>
        get() = _loginSuccess
    private val _loginSuccess = MutableLiveData<HashMap<Boolean, String?>>()

    val userLogged: LiveData<Boolean>
        get() = _userLogged
    private val _userLogged = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            val isLogged = authRepository.isLogged()
            _userLogged.postValue(isLogged)
        }
    }

    fun loginSuccess(activity: Activity, email: String, password: String) {
        viewModelScope.launch {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                authRepository.login(activity, email, password) { success, message ->
                    val map = hashMapOf<Boolean, String?>()
                    map[success] = message
                    _loginSuccess.postValue(map)
                }
            }
        }
    }

}