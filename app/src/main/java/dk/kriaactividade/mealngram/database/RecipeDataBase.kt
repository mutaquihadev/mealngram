package dk.kriaactividade.mealngram.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dk.kriaactividade.mealngram.database.converter.Converters
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.database.room.SelectableRecipe

@Database(entities = [RecipeEntity::class, SelectableRecipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDataBase  : RoomDatabase() {

    abstract fun recipeDAO(): RecipeDAO
    abstract fun recipeWeekDAO(): SelectableRecipeDAO

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
                    "recipes_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}