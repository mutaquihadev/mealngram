package dk.kriaactividade.mealngram.presentation.recipeDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.HandleGetState
import dk.kriaactividade.mealngram.presentation.recipeDetails.RecipeDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class RecipeOfDayUiData(
    val selectedRecipe: SelectableRecipe
)

sealed interface RecipeOfDayUiState {
    object Loading : RecipeOfDayUiState
    object Error : RecipeOfDayUiState
    data class Success(val uiData: RecipeOfDayUiData) : RecipeOfDayUiState
}

@HiltViewModel
class RecipeOfDayViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel(), HandleGetState<List<SelectableRecipe>> {

    private val _uiState: MutableStateFlow<RecipeOfDayUiState> =
        MutableStateFlow(RecipeOfDayUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
          repository.getAllSelectedRecipes().collect(::handleGetState)
        }
    }

    override fun handleGetState(state: DataState<List<SelectableRecipe>>) {
        when(state){
            is DataState.Data ->{
                state.data.forEach {
                    if (compareDay(it.dateWeek) == getCurrentDate()){
                        val selectedRecipe =  SelectableRecipe(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            ingredients = it.ingredients,
                            image = it.image,
                            dateWeek = it.dateWeek,
                            weekNumber = it.weekNumber
                        )
                        _uiState.value = RecipeOfDayUiState.Success(RecipeOfDayUiData(selectedRecipe))
                    }
                }
            }
        }
    }

    private fun compareDay(dateSave: Date): Int {
        val date = Date(dateSave.time)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return day + month + year
    }

    private fun getCurrentDate(): Int {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return day + month + year
    }


}