package dk.kriaactividade.mealngram.presentation.recipesSelected

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.HandleGetState
import dk.kriaactividade.mealngram.presentation.utils.formatDateForLiteral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class RecipeListDetailsUiData(
    val recipes: List<RecipesSelectedItem>
)

sealed interface RecipeListDetailsUiState {
    object Loading : RecipeListDetailsUiState
    object Error : RecipeListDetailsUiState
    data class Success(val uiData: RecipeListDetailsUiData) : RecipeListDetailsUiState
}

@HiltViewModel
class RecipesSelectedViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel(), HandleGetState<List<RecipesSelectedItem>> {

    private val recipesSelected = mutableListOf<RecipesSelectedItem>()

    private val _uiState: MutableStateFlow<RecipeListDetailsUiState> =
        MutableStateFlow(RecipeListDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val listDateWeek: LiveData<MutableList<Pair<Date, Date>>>
        get() = _listDateWeek
    private val _listDateWeek =
        MutableLiveData<MutableList<Pair<Date, Date>>>()


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

    init {
        viewModelScope.launch {
            getCurrentWeek()
        }
    }

//    suspend fun getRoomList(): List<RecipeRoomWeekItem> {
//        return repository.getAllRecipesWeek()
//    }

    private fun getCurrentWeek() {
        val listDaysOnWeek = mutableListOf<Pair<Date, Date>>()
        val calendar = Calendar.getInstance()
        val week = calendar.get(Calendar.WEEK_OF_YEAR)
        val listWeek: List<Int> = listOf(0, 1, 2, 3)
        listWeek.map {
            listDaysOnWeek.add(getCurrent(week + it))
        }
        _listDateWeek.postValue(listDaysOnWeek)
    }

    private fun getCurrent(weekInt: Int): Pair<Date, Date> {
        val data = Calendar.getInstance()
        data.firstDayOfWeek = Calendar.MONDAY
        data.set(Calendar.WEEK_OF_YEAR, weekInt)
        data.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val initial = data.time
        data.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val final = data.time
        return Pair(initial,final)
    }

    fun convertDateForString(initialDate: Date, finalDate: Date): String {
        return "${initialDate.formatDateForLiteral()} á ${finalDate.formatDateForLiteral()}"
    }

    fun getWeekNumber(weekNumber: Int): Long {
        val data = Calendar.getInstance()
        return if (weekNumber == 0) {
            data.time.time
        } else {
            val week = data.get(Calendar.WEEK_OF_YEAR)
            data.firstDayOfWeek = Calendar.MONDAY
            val weekSelected = week + weekNumber
            data.set(Calendar.WEEK_OF_YEAR, weekSelected)
            data.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            data.time.time
        }
    }
}