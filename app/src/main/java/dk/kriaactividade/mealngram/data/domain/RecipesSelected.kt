package dk.kriaactividade.mealngram.data.domain

import java.io.Serializable

data class RecipesSelected(
    val name: String = "",
    val image: String =""
) : Serializable