package dk.kriaactividade.mealngram.entities.domain.extensions

import dk.kriaactividade.mealngram.entities.domain.recipe.WEEK
import java.util.*

fun Date.toWeek(): WEEK {
    val calendar = Calendar.getInstance()
    calendar.time = this

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> WEEK.MONDAY
        Calendar.TUESDAY -> WEEK.TUESDAY
        Calendar.WEDNESDAY -> WEEK.WEDNESDAY
        Calendar.THURSDAY -> WEEK.THURSDAY
        Calendar.FRIDAY -> WEEK.FRIDAY
        Calendar.SATURDAY -> WEEK.SATURDAY
        Calendar.SUNDAY -> WEEK.SUNDAY
        else -> {
            WEEK.MONDAY
        }
    }
}