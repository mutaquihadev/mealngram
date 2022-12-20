package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem
import dk.kriaactividade.mealngram.database.room.RecipeWeekRepository
import dk.kriaactividade.mealngram.helpers.DataState
import dk.kriaactividade.mealngram.helpers.HandleGetState
import dk.kriaactividade.mealngram.presentation.utils.formatDateForLiteral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.HashSet

data class RecipeListDetailsUiData(
    val recipes: List<RecipesSelectedItem>
)

sealed interface RecipeListDetailsUiState {
    object Loading : RecipeListDetailsUiState
    object Error : RecipeListDetailsUiState
    data class Success(val uiData: RecipeListDetailsUiData) : RecipeListDetailsUiState
}

class RecipesSelectedViewModel @Inject constructor(private val repository: RecipeWeekRepository) :
    ViewModel(), HandleGetState<List<RecipesSelectedItem>> {

    private val recipesSelected = mutableListOf<RecipesSelectedItem>()

    private val _uiState: MutableStateFlow<RecipeListDetailsUiState> =
        MutableStateFlow(RecipeListDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val listDateWeek: LiveData<MutableList<kotlin.collections.HashMap<Date,Date>>>
    get() = _listDateWeek
    private val _listDateWeek = MutableLiveData<MutableList<kotlin.collections.HashMap<Date,Date>>>()


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

    suspend fun getRoomList(): List<RecipeRoomWeekItem> {
        return repository.allRecipes()
    }

    fun getCurrentWeek(){
        val listDaysOnWeek = mutableListOf<HashMap<Date,Date>>()
        val calendar = Calendar.getInstance()
        val week = calendar.get(Calendar.WEEK_OF_YEAR)
        val listWeek: List<Int> = listOf(0, 1, 2, 3)
        listWeek.map {
            listDaysOnWeek.add(getCurrent(week + it))
        }
        _listDateWeek.postValue(listDaysOnWeek)
    }

    private fun getCurrent(weekInt: Int): HashMap<Date, Date> {
        val daysOnWeek = hashMapOf<Date,Date>()
        val data = Calendar.getInstance()
        data.firstDayOfWeek = Calendar.MONDAY
        data.set(Calendar.WEEK_OF_YEAR, weekInt)
        data.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val initial = data.time
        data.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val final = data.time
        daysOnWeek[initial] = final
        return daysOnWeek
    }



}