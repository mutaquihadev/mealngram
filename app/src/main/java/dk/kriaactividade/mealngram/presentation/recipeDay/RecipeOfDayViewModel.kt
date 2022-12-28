package dk.kriaactividade.mealngram.presentation.recipeDay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.entities.ui.currentDayRecipe.RecipeOfDayUiState
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.HandleGetState
import dk.kriaactividade.mealngram.presentation.utils.isSameDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecipeOfDayViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel(), HandleGetState<List<SelectableRecipe>> {

    private val _uiState: MutableStateFlow<RecipeOfDayUiState> = MutableStateFlow(RecipeOfDayUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
          //repository.getAllSelectedRecipes().collect(::handleGetState)
        }
    }

    override fun handleGetState(state: DataState<List<SelectableRecipe>>) {
        when(state){
            is DataState.Data -> {
                val today = Calendar.getInstance().time
                val currentDaySelectedRecipe = state.data.first { it.dateWeek.isSameDay(today) }

                _uiState.value = RecipeOfDayUiState.Success(currentDaySelectedRecipe)
            }
            else -> {}
        }
    }
}