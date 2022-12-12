package dk.kriaactividade.mealngram.presentation.favorite.selectFavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListUiData
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SelectFavoriteUiData(
    val selectedFavorites: List<SelectFavoriteItem> = listOf(),
)

sealed interface SelectFavoriteUiState {
    object Loading : SelectFavoriteUiState
    object Error : SelectFavoriteUiState
    data class Success(val uiData: SelectFavoriteUiData) : SelectFavoriteUiState
}

class SelectFavoriteRecipesViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<SelectFavoriteUiState> =
        MutableStateFlow(SelectFavoriteUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
        }
    }

    private fun handleGetAllRecipes(state: DataState<List<RecipeItem>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Loading -> {
                _uiState.value = SelectFavoriteUiState.Loading
            }
            is DataState.Data -> {
               val selectedFavorite =  state.data.map { recipeItem ->
                    SelectFavoriteItem(
                        id = recipeItem.id,
                        name = recipeItem.name,
                        description = recipeItem.description,
                        image = recipeItem.image,
                        ingredients = recipeItem.ingredients
                    )
                }
                _uiState.value =
                    SelectFavoriteUiState.Success(uiData = SelectFavoriteUiData(selectedFavorites = selectedFavorite))
            }
        }
    }

}