package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.data.domain.WEEK
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import dk.kriaactividade.mealngram.database.room.RecipeRoomItem
import dk.kriaactividade.mealngram.database.room.RoomRepository
import dk.kriaactividade.mealngram.helpers.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class RecipeListUiData(
    val showButton: Boolean = false,
    val showProgress: Boolean = false,
    val progressValue: Int = 0,
    val recipeRoomItem: List<RecipeRoomItem> = listOf(),
    val recipes: List<RecipeItem> = listOf(),
    val completeSelection: MutableList<RecipesDetails> = mutableListOf()
)

sealed interface RecipeListUiState {
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
    data class SaveCache(val uiData: RecipeListUiData) : RecipeListUiState
    data class Success(val uiData: RecipeListUiData) : RecipeListUiState
    data class CompleteSelection(val complete: RecipeListUiData) : RecipeListUiState
}

interface RecipeListViewModelItemActions {
    fun onDaySelected(recipeId: Int, weekDay: WEEK, date: Date)
}

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel(),
    RecipeListViewModelItemActions {
    @Inject
    lateinit var room: RoomRepository
    private var isEditMode: Boolean = false
    private var valueProgress: Int = 0
    private var updatedSelectedDays = listOf<SelectedChipState>()
    private var showButton = false
    private val recipeListLocal = mutableListOf<RecipesDetails>()

    private val _uiState: MutableStateFlow<RecipeListUiState> =
        MutableStateFlow(RecipeListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val selectedChipStates: List<SelectedChip> by lazy {
        val calendar = Calendar.getInstance()
        calendar.daysUntilTheEndOfWeek().map {
            SelectedChip(date = it, weekDay = it.toWeek())
        }
    }

    init {
        viewModelScope.launch {
            repository.getAllRecipes().collect(::handleGetAllRecipes)
            room.allRecipes()
        }
    }

    private fun handleGetAllRecipes(state: DataState<List<RecipeItem>>) {
        when (state) {
            is DataState.Error -> {}
            is DataState.Data -> {
                _uiState.value =
                    RecipeListUiState.Success(uiData = RecipeListUiData(recipes = state.data))
            }
            is DataState.Loading -> _uiState.value = RecipeListUiState.Loading
            is DataState.SaveCache -> {
                val recipeLocal = state.saveState.map { recipeItem ->
                    RecipeRoomItem(
                        id = recipeItem.id,
                        name = recipeItem.name,
                        description = recipeItem.description,
                        ingredients = recipeItem.ingredients,
                        image = recipeItem.image,
                        dateInserted = getCurrentDate()
                    )
                }
                viewModelScope.launch {
                    if (room.allRecipes().isEmpty()) {
                        room.insertList(recipeLocal)
                    } else {
                        var recipeLocalDate: Date? = null

                        room.allRecipes()
                            .forEach { dateLocal -> recipeLocalDate = dateLocal.dateInserted }

                        if (recipeLocalDate?.let { getCurrentDateString(it) } != getCurrentDateString(getCurrentDate())){
                            room.insertList(recipeLocal)
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    private fun getCurrentDateString(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        return simpleDateFormat.format(date)
    }

    fun updateEditMode() {
        isEditMode = !isEditMode
        _uiState.value.let {
            when (it) {
                RecipeListUiState.Error -> {}
                RecipeListUiState.Loading -> {}
                is RecipeListUiState.Success -> {
                    val updatedRecipes = it.uiData.recipes.map { recipe ->
                        RecipeItem(
                            id = recipe.id,
                            name = recipe.name,
                            description = recipe.description,
                            ingredients = recipe.ingredients,
                            image = recipe.image,
                            isSelectionMode = isEditMode,
                            selectedDays = recipe.selectedDays
                        )
                    }

                    _uiState.value = RecipeListUiState.Success(
                        uiData = RecipeListUiData(
                            recipes = updatedRecipes,
                            showProgress = isEditMode
                        )
                    )
                }
            }
        }

    }

    private fun clearSelectionMode() {
//        val clearedRecipes = recipes.map { recipe ->
//            RecipeItem(
//                id = recipe.id,
//                name = recipe.name,
//                description = recipe.description,
//                ingredients = recipe.ingredients,
//                image = recipe.image,
//                isSelectionMode = false,
//                selectedDays = recipe.selectedDays
//            )
//        }
//
//        recipes.clear()
//        recipes.addAll(clearedRecipes)
    }


    private fun clearSelectedRecipes() {
        selectedChipStates.forEach { it.recipeId = null }
    }


    override fun onDaySelected(recipeId: Int, weekDay: WEEK, date: Date) {

        _uiState.value.let {
            when (it) {
                RecipeListUiState.Error -> {}
                RecipeListUiState.Loading -> {}
                is RecipeListUiState.Success -> {
                    val recipe = it.uiData.recipes.first { recipeItem -> recipeItem.id == recipeId }
                    val selectableDays = recipe.selectedDays

                    val isCurrentSelectedDayChecked =
                        selectableDays.first { selectableDay -> selectableDay.week == weekDay }.isChecked
                    val isCurrentSelectedDayCheckedUpdated = !isCurrentSelectedDayChecked

                    val selected =
                        selectedChipStates.first { selectedChipState -> selectedChipState.weekDay == weekDay }

                    val (updatedRecipeId, updatedIsChecked) = if (isCurrentSelectedDayCheckedUpdated) Pair(
                        recipeId,
                        true
                    ) else Pair(null, false)
                    selected.recipeId = updatedRecipeId
                    selected.isChecked = updatedIsChecked

                    val updatedRecipes = it.uiData.recipes.map { recipeItem ->

                        updatedSelectedDays =
                            selectedChipStates.toSelectedChipStates(recipeItemId = recipeItem.id)

                        RecipeItem(
                            id = recipeItem.id,
                            name = recipeItem.name,
                            description = recipeItem.description,
                            ingredients = recipe.ingredients,
                            image = recipeItem.image,
                            isSelectionMode = true,
                            selectedDays = updatedSelectedDays
                        )
                    }

                    if (recipeId == selected.recipeId) {
                        goToCompleteSelection(recipe, false)
                    } else {
                        goToCompleteSelection(recipe, true)
                    }


                    if (updatedIsChecked) {
                        valueProgress += 100 / updatedSelectedDays.size + 1
                    } else {
                        valueProgress -= 100 / updatedSelectedDays.size + 1
                    }

                    showButton = valueProgress >= 100

                    _uiState.value = RecipeListUiState.Success(
                        uiData = RecipeListUiData(
                            recipes = updatedRecipes,
                            showProgress = true,
                            progressValue = valueProgress,
                            showButton = showButton
                        )
                    )

                }
            }
        }
    }

    private fun goToCompleteSelection(selectedItem: RecipeItem, removeIt: Boolean) {
        val recipeDetails = RecipesDetails(
            id = selectedItem.id,
            name = selectedItem.name,
            description = selectedItem.description,
            ingredients = selectedItem.ingredients,
            image = selectedItem.image,
        )

        if (removeIt) {
            recipeListLocal.remove(recipeDetails)
        } else {
            recipeListLocal.add(recipeDetails)
        }
        _uiState.value = RecipeListUiState.CompleteSelection(
            RecipeListUiData(
                completeSelection = recipeListLocal
            )
        )
    }
}

data class SelectedChip(
    val date: Date,
    val weekDay: WEEK,
    var recipeId: Int? = null,
    var isChecked: Boolean = false
)

fun List<SelectedChip>.toSelectedChipStates(recipeItemId: Int): List<SelectedChipState> {
    return this.map { selectedChip ->

        val chipState = if (selectedChip.recipeId == null) {
            ChipStateOptions.NOT_SELECTED
        } else if (selectedChip.recipeId != recipeItemId) {
            ChipStateOptions.DISABLED
        } else {
            if (selectedChip.isChecked) {
                ChipStateOptions.SELECTED
            } else {
                ChipStateOptions.NOT_SELECTED
            }
        }

        when (chipState) {
            ChipStateOptions.NOT_SELECTED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = true,
                    isChecked = false
                )
            }
            ChipStateOptions.SELECTED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = true,
                    isChecked = true
                )
            }
            ChipStateOptions.DISABLED -> {
                SelectedChipState(
                    date = selectedChip.date,
                    id = recipeItemId,
                    week = selectedChip.weekDay,
                    isSelectable = false,
                    isChecked = false
                )
            }
        }
    }
}

enum class ChipStateOptions {
    NOT_SELECTED,
    SELECTED,
    DISABLED
}