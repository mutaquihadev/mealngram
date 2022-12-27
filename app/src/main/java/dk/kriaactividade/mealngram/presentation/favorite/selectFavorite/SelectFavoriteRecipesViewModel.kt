package dk.kriaactividade.mealngram.presentation.favorite.selectFavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.RecipeDTO
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.presentation.utils.Preferences
import dk.kriaactividade.mealngram.presentation.utils.convertStringForInt
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

interface SelectFavoriteRecipesViewActions {
    fun onFavoriteItem(favoriteRecipeId: Int)
}

class SelectFavoriteRecipesViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel(), SelectFavoriteRecipesViewActions {

    private val favoriteRecipeIds = mutableListOf<Int>()

    private val _uiState: MutableStateFlow<SelectFavoriteUiState> =
        MutableStateFlow(SelectFavoriteUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
        }
    }

    private fun handleGetAllRecipes(state: DataState<List<RecipeEntity>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Loading -> {
                _uiState.value = SelectFavoriteUiState.Loading
            }
            is DataState.Data -> {
                var idPrefs = 0
                Preferences.getRecipeListId()?.forEach { idPrefs = it.convertStringForInt() }
                val selectedFavorite = state.data.map { recipeItem ->
                    val isFavorite = idPrefs == recipeItem.id
                    SelectFavoriteItem(
                        id = recipeItem.id,
                        name = recipeItem.name,
                        description = recipeItem.description,
                        image = recipeItem.image,
                        ingredients = recipeItem.ingredients,
                        isFavorite = isFavorite
                    )
                }
                _uiState.value =
                    SelectFavoriteUiState.Success(uiData = SelectFavoriteUiData(selectedFavorites = selectedFavorite))
            }
        }
    }

    override fun onFavoriteItem(favoriteRecipeId: Int) {
        uiState.value.let { state ->
            when (state) {
                is SelectFavoriteUiState.Success -> {
                    var favoriteList = state.uiData.selectedFavorites.map {
                        val isFavoriteUpdated = if (it.id == favoriteRecipeId) {
                            !it.isFavorite
                        } else {
                            it.isFavorite
                        }
                        SelectFavoriteItem(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            ingredients = it.ingredients,
                            image = it.image,
                            isFavorite = isFavoriteUpdated
                        )
                    }

                    favoriteList.forEach { favoriteItem ->
                        if (favoriteItem.isFavorite) {
                            favoriteRecipeIds.add(favoriteItem.id)
                            Preferences.setRecipeListId(favoriteItem.id)
                            favoriteRecipeIds.remove(favoriteItem.id)
                        }

                    }

                    _uiState.value = SelectFavoriteUiState.Success(
                        uiData = SelectFavoriteUiData(selectedFavorites = favoriteList)
                    )
                }
            }
        }
    }
}