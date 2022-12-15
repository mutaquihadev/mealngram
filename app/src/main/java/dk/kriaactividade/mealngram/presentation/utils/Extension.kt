package dk.kriaactividade.mealngram.presentation.utils

import android.view.View
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
