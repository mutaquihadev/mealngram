package dk.kriaactividade.mealngram.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.converter.Converters

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDataBase  : RoomDatabase() {

    abstract fun recipeDAO(): RecipeDAO

    companion object {
        @Volatile
        private var INSTANCE: RecipeDataBase? = null

        fun getDatabase(
            context: Context,
        ): RecipeDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDataBase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()

                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}