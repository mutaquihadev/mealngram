package dk.kriaactividade.mealngram.presentation.authentication.register

import android.app.Activity
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepositoryImp
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Calendar.*
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val repositoryImp: RecipesRepositoryImp) : ViewModel() {

    val isEmail:LiveData<Boolean>
    get() = _isEmail
    private val _isEmail = MutableLiveData<Boolean>()

    val isPassword:LiveData<Boolean>
        get() = _isPassword
    private val _isPassword = MutableLiveData<Boolean>()

    val birthday: LiveData<String>
        get() = _birthday
    private val _birthday = MutableLiveData<String>()

    val successRegister:LiveData<HashMap<Boolean,String?>>
    get() = _successRegister
    private val _successRegister = MutableLiveData<HashMap<Boolean,String?>>()

    fun getBirthday(day: Int, month: Int, year: Int) {
        val monthInter = month + 1
        if (month < 10 && day < 10) {
            _birthday.postValue("0$day/0$monthInter/$year")
        } else if (day < 10) {
            _birthday.postValue("0$day/$monthInter/$year")
        } else if (month < 10) {
            _birthday.postValue("$day/0$monthInter/$year")
        }else{
            _birthday.postValue("$day/$monthInter/$year")
        }
    }

    fun confirmPassword(password:CharSequence, confirmPassword:CharSequence){
        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)){
            _isPassword.postValue(false)
        }else if (password == confirmPassword){
            _isPassword.postValue(true)
        }else{
            _isPassword.postValue(false)
        }
    }

     fun validateEmail(target:CharSequence){
         if (TextUtils.isEmpty(target)){
             _isEmail.postValue(false)
         }else{
             _isEmail.postValue(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
         }
    }

    private fun getCalendar():Calendar{
        return Calendar.getInstance()
    }

    fun getYear():Int{
      return getCalendar().get(YEAR)
    }

    fun getMonth():Int{
        return getCalendar().get(MONTH)
    }

    fun getDay():Int{
        return getCalendar().get(DAY_OF_MONTH)
    }

  fun register(activity: Activity, email:String,password:String){
      viewModelScope.launch {
          if (email.isNotEmpty() && password.isNotEmpty()){
              repositoryImp.registerUser(activity,email,password){response, message ->
                  val map = hashMapOf<Boolean,String?>()
                  map[response] = message
                  _successRegister.postValue(map)
              }
          }

      }
  }
}