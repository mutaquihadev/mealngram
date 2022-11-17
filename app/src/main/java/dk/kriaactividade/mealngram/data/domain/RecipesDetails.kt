package dk.kriaactividade.mealngram.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.Date

@Parcelize
class DetailsRecipes(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val dayOfWeek: WEEK,
    val day: String
) : Parcelable