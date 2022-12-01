package dk.kriaactividade.mealngram.presentation.authentication.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dk.kriaactividade.mealngram.data.repository.RecipesRepositoryImp
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repositoryImp: RecipesRepositoryImp): ViewModel() {


    val loginSuccess: LiveData<Boolean>
    get() = _loginSuccess
    private val _loginSuccess = MutableLiveData<Boolean>()

    val userLogged: LiveData<Boolean>
        get() = _userLogged
    private val _userLogged = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            repositoryImp.getIsLogged {
                _userLogged.postValue(it)
            }
        }
    }

    fun loginSuccess(activity: Activity, email:String, password:String){
        viewModelScope.launch {
            repositoryImp.getLogin(activity,email,password){
                _loginSuccess.postValue(it)
            }
        }
    }

}