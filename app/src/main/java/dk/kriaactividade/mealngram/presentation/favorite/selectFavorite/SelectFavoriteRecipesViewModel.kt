package dk.kriaactividade.mealngram.presentation.favorite.selectFavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.utils.DataStore
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

    private var isFavorite = false
    private val dataStore = DataStore()

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
                val selectedFavorite = state.data.map { recipeItem ->
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

    override fun onFavoriteItem(favoriteRecipeId: Int) {
        viewModelScope.launch {
            dataStore.saveRecipeId(DataStoreSaveId(id = favoriteRecipeId))
            dataStore.getUserFromPreferencesStore().collect(::handleGetFavorite)
        }
    }

    private fun handleGetFavorite(dataSave: DataStoreSaveId) {
        isFavorite = !isFavorite
        uiState.value.let { state ->
            when (state) {
                is SelectFavoriteUiState.Success -> {
                    val list = state.uiData.selectedFavorites.map {
                        if (it.id == dataSave.id) {
                            SelectFavoriteItem(
                                id = it.id,
                                name = it.name,
                                description = it.description,
                                ingredients = it.ingredients,
                                image = it.image,
                                isFavorite = true
                            )
                        } else {
                            SelectFavoriteItem(
                                id = it.id,
                                name = it.name,
                                description = it.description,
                                ingredients = it.ingredients,
                                image = it.image,
                                isFavorite = false
                            )
                        }
                    }
                    _uiState.value = SelectFavoriteUiState.Success(
                        uiData = SelectFavoriteUiData(selectedFavorites = list)
                    )


                }
                else -> {}
            }
        }
    }

}