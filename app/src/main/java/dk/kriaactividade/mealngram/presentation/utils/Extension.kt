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
    } catch (error: Exception) {
        error.printStackTrace()
        -1
    }
}

private fun getStringForFormat(value: Date): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM")
    return simpleDateFormat.format(value)
}

fun Date.formatDateForLiteral(): String {
    return getStringForFormat(this)
}

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(key)

fun Fragment.setNavigationResult(result: Int, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun Long.getWeekNumber(): Int {
    val date = Date(this)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val week = calendar.get(Calendar.WEEK_OF_YEAR)
    return week
}

fun Date.isSameDay(valueDate: Date): Boolean {
    val dateInserted = Date(this.time)
    val calendar = Calendar.getInstance()
    calendar.time = dateInserted
    val dayInserted = calendar.get(Calendar.DAY_OF_MONTH)
    val monthInserted = calendar.get(Calendar.MONTH)
    val yearInserted = calendar.get(Calendar.YEAR)

    val dateCurrent = Date(valueDate.time)
    calendar.time = dateCurrent
    val dayCurrent = calendar.get(Calendar.DAY_OF_MONTH)
    val monthCurrent = calendar.get(Calendar.MONTH)
    val yearCurrent = calendar.get(Calendar.YEAR)

    return dayInserted == dayCurrent && monthInserted == monthCurrent && yearInserted == yearCurrent
}
