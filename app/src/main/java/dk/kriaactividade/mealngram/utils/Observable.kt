package dk.kriaactividade.mealngram.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Observable {
    val valueProgress: LiveData<Int>
        get() = _valueProgress
    private val _valueProgress = MutableLiveData<Int>()

    fun getValueProgress(value:Int){
        _valueProgress.postValue(value)
    }
}