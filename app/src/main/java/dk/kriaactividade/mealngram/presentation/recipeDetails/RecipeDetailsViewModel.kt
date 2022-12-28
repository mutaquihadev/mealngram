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


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val repository: RecipesRepository, savedStateHandle: SavedStateHandle) :
    ViewModel(),HandleGetState<List<SelectableRecipe>> {

    private val weekNumber = savedStateHandle.get<Int>("weekNumber")

    private val _uiState: MutableStateFlow<List<SelectableRecipe>> =
        MutableStateFlow(emptyList())
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
                _uiState.value = state.data
            }
        }
    }
}