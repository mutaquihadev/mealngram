package dk.kriaactividade.mealngram.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val logoutSuccess: LiveData<Boolean>
        get() = _logoutSuccess
    private val _logoutSuccess = MutableLiveData<Boolean>()

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
           val isLogged = authRepository.isLogged()
            _logoutSuccess.postValue(isLogged)
        }
    }

}