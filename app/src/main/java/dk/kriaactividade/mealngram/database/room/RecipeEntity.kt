package dk.kriaactividade.mealngram.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_recipe")
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val dateInserted: Date
)

@Entity(tableName = "table_recipe_week")
data class RecipeRoomWeekItem(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int = 0,
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val dateWeek:Date,
    val weekNumber:Int
)