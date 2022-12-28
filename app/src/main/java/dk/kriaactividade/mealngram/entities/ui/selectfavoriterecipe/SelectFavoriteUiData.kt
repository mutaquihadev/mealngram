package dk.kriaactividade.mealngram.entities.ui.selectfavoriterecipe

import dk.kriaactividade.mealngram.entities.domain.recipe.SelectFavoriteItem

data class SelectFavoriteUiData(
    val selectedFavorites: List<SelectFavoriteItem> = listOf(),
)
