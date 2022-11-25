package dk.kriaactividade.mealngram.presentation.authentication.register

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Calendar.*
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : ViewModel() {

    val isErrorEmail:LiveData<Boolean>
    get() = _isErrorEmail
    private val _isErrorEmail = MutableLiveData<Boolean>()

    val isPassword:LiveData<Boolean>
        get() = _isPassword
    private val _isPassword = MutableLiveData<Boolean>()

    val birthday: LiveData<String>
        get() = _birthday
    private val _birthday = MutableLiveData<String>()

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

    fun confirmPassword(password:String, confirmPassword:String){
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
             _isErrorEmail.postValue(false)
         }else{
             _isErrorEmail.postValue(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
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
}