package dk.kriaactividade.mealngram.entities.ui.selectfavoriterecipe

sealed interface SelectFavoriteUiState {
    object Loading : SelectFavoriteUiState
    object Error : SelectFavoriteUiState
    data class Success(val uiData: SelectFavoriteUiData) : SelectFavoriteUiState
}