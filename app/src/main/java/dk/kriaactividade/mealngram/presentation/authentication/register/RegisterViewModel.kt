package dk.kriaactividade.mealngram.presentation.authentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Calendar.*
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : ViewModel() {

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