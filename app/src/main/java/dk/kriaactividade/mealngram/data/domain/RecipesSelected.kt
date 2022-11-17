package dk.kriaactividade.mealngram.data.domain

import java.io.Serializable

data class RecipesSelected(
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val day: String = "",
    val dayOfWeek: WEEK?= null,
    val ingredients: List<String> = listOf()
) : Serializable