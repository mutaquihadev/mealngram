package dk.kriaactividade.mealngram.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Observable {
    val valueProgress: LiveData<Int>
        get() = _valueProgress
    private val _valueProgress = MutableLiveData<Int>()

    val verifyDayOfWeek :LiveData<String>
    get() = _verifyDayOfWeek
    private val _verifyDayOfWeek = MutableLiveData<String>()

    val isActive: LiveData<Boolean>
    get() = _isActive
    private val _isActive = MutableLiveData<Boolean>()

    fun getValueProgress(value:Int){
        _valueProgress.postValue(value)
    }

    fun setVisibilityChip(value: String){
        _verifyDayOfWeek.postValue(value)
    }

    fun activeChipIsTrue(value: Boolean){
        _isActive.postValue(value)
    }
}