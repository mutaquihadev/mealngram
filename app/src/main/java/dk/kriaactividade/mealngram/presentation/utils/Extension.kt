package dk.kriaactividade.mealngram.presentation.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dk.kriaactividade.mealngram.presentation.recipeList.toWeek
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

private fun replaceString(value: String): String {
    return value.replace("[", "").replace(",", "").replace("]", "")
}

fun String.convertStringForInt(): Int {
    var stringFotInt = ""
    return try {
        stringFotInt = replaceString(this)
        stringFotInt.toInt()
    }catch (error:Exception){
        error.printStackTrace()
        -1
    }
}

private fun getStringForFormat(value: Date):String{
    val simpleDateFormat = SimpleDateFormat("dd MMM")
    return simpleDateFormat.format(value)
}

fun Date.formatDateForLiteral():String{
    return getStringForFormat(this)
}

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(key)

fun Fragment.setNavigationResult(result: Boolean, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}
