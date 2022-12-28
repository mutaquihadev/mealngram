package dk.kriaactividade.mealngram.entities.domain

import java.util.*

fun Calendar.daysUntilTheEndOfWeek(): List<Date> {
    val dates = mutableListOf<Date>()
    do {
        dates.add(this.time)
        this.add(Calendar.DAY_OF_MONTH, 1)
    } while (this.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)

    return dates.toList()
}