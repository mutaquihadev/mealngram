package dk.kriaactividade.mealngram.presentation.recipesSelected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.HandleGetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeListDetailsUiData(
    val recipes: List<RecipesSelectedItem>
)

sealed interface RecipeListDetailsUiState {
    object Loading : RecipeListDetailsUiState
    object Error : RecipeListDetailsUiState
    data class Success(val uiData: RecipeListDetailsUiData) : RecipeListDetailsUiState
}

class RecipesSelectedViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel(), HandleGetState<List<RecipesSelectedItem>> {

    private val recipesSelected = mutableListOf<RecipesSelectedItem>()

    private val _uiState: MutableStateFlow<RecipeListDetailsUiState> =
        MutableStateFlow(RecipeListDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getSelectedRecipes().collect(::handleGetState)
        }
    }

    override fun handleGetState(state: DataState<List<RecipesSelectedItem>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Data -> {
                recipesSelected.clear()
                recipesSelected.addAll(state.data)
                _uiState.value =
                    RecipeListDetailsUiState.Success(uiData = RecipeListDetailsUiData(recipes = recipesSelected))
            }
            is DataState.Loading -> _uiState.value = RecipeListDetailsUiState.Loading
        }
    }


}