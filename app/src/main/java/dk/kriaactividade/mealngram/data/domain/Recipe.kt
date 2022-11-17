package dk.kriaactividade.mealngram.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dk.kriaactividade.mealngram.database.converter.Converters

@Entity(tableName = "table_recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val myId: Int = 0,
    @ColumnInfo(name = "recipe")
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = listOf(),
    val image: String = "",
    val video: String? = "",
    val mainImage: String? = null,
    val isSelectionMode: Boolean = false,
    var dayOfWeekSelectedPair: List<ChipState> = listOf(
        ChipState(id = id, WEEK.MONDAY),
        ChipState(id = id, WEEK.TUESDAY),
        ChipState(id = id, WEEK.WEDNESDAY),
        ChipState(id = id, WEEK.THURSDAY),
        ChipState(id = id, WEEK.FRIDAY),
        ChipState(id = id, WEEK.SATURDAY),
        ChipState(id = id, WEEK.SUNDAY)
    )

)

data class ChipState(
    val id: Int, val dayOfWeek: WEEK, val isActive: Boolean = false, val isVisible: Boolean = true
)

enum class WEEK(val id: Int, val label: String) {
    MONDAY(id = 0, label = "M"),
    TUESDAY(id = 1, label = "T"),
    WEDNESDAY(id = 2, label = "W"),
    THURSDAY(id = 3, label = "Th"),
    FRIDAY(id = 4, label = "F"),
    SATURDAY(id = 5, "Sa"),
    SUNDAY(id = 6, label = "S")
}