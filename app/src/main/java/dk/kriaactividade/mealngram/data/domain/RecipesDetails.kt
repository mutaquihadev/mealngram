package dk.kriaactividade.mealngram.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 class DetailsRecipes(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val dayOfWeek: WEEK
) : Parcelable