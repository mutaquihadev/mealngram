package dk.kriaactividade.mealngram.presentation.utils

import android.icu.util.Calendar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Util {
     fun getCurrentDate():String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        return dateFormat.format(calendar.time.time)
    }

     fun getDay(day: Int): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val calendar = java.util.Calendar.getInstance()
        calendar.firstDayOfWeek = java.util.Calendar.SUNDAY
        val dayWeek = calendar[java.util.Calendar.DAY_OF_WEEK]
        when (day) {
            0 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.MONDAY - dayWeek)
            }
            1 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.TUESDAY - dayWeek)
            }
            2 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.WEDNESDAY - dayWeek)
            }
            3 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.THURSDAY - dayWeek)
            }
            4 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.FRIDAY - dayWeek)
            }
            5 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.SATURDAY - dayWeek)
            }
            6 -> {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.SUNDAY - dayWeek)
            }
        }
        return dateFormat.format(calendar.time)
    }
}