package dk.kriaactividade.mealngram.presentation.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.HandleGetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeDetailsUiData(
    val recipes: List<SelectableRecipe>
)

sealed interface RecipeDetailsUiState {
    object Loading : RecipeDetailsUiState
    object Error : RecipeDetailsUiState
    data class Success(val uiData: RecipeDetailsUiData) : RecipeDetailsUiState
}

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository, private val savedStateHandle: SavedStateHandle) :
    ViewModel(),HandleGetState<List<SelectableRecipe>> {

    val weekNumber = savedStateHandle.get<Int>("weekNumber")

    private val _uiState: MutableStateFlow<RecipeDetailsUiState> =
        MutableStateFlow(RecipeDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            weekNumber?.let {
                repository.selectedRecipes(it).collect(::handleGetState)
            }
        }
    }

    override fun handleGetState(state: DataState<List<SelectableRecipe>>) {
        when(state){
            is DataState.Data -> {
                _uiState.value = RecipeDetailsUiState.Success(RecipeDetailsUiData(state.data))
            }
        }
    }

}