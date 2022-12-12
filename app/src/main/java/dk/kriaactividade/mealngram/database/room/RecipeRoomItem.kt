package dk.kriaactividade.mealngram.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_recipe")
data class RecipeRoomItem(
    @PrimaryKey(autoGenerate = true)
    val myId: Int = 0,
    @ColumnInfo(name = "recipe")
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val dateInserted: Date
)