package dk.kriaactividade.mealngram.presentation.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.kriaactividade.mealngram.data.domain.*
import dk.kriaactividade.mealngram.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class RecipeListViewModel @Inject constructor(private val repository: RecipesRepository) :
    ViewModel() {
    private var countProgress = 0
    private val selectedChipStates = mutableListOf(
        SelectedChip(WEEK.MONDAY),
        SelectedChip(WEEK.TUESDAY),
        SelectedChip(WEEK.WEDNESDAY),
        SelectedChip(WEEK.THURSDAY),
        SelectedChip(WEEK.FRIDAY),
        SelectedChip(WEEK.SATURDAY),
        SelectedChip(WEEK.SUNDAY)
    )

    val recipes: LiveData<List<Recipe>>
        get() = _recipes
    private val _recipes = MutableLiveData<List<Recipe>>()

    val addDetailsRecipes: LiveData<DetailsRecipes>
        get() = _addDetailsRecipes
    private val _addDetailsRecipes = MutableLiveData<DetailsRecipes>()

    val valueProgress: LiveData<Int>
        get() = _valueProgress
    private val _valueProgress = MutableLiveData<Int>()

    val showButton: LiveData<Boolean>
        get() = _showButton
    private val _showButton = MutableLiveData<Boolean>()

    val isEditMode: LiveData<Boolean>
        get() = _isEditMode
    private val _isEditMode = MutableLiveData<Boolean>(false)

    init {
        viewModelScope.launch {
            repository.getRecipes {
                _recipes.postValue(it)
            }
        }
    }

    private fun getValueProgress(value: Boolean) {
        val valueProgress: Double = 10.0 / 0.7 + 1
        val valueInt = valueProgress.roundToInt()
        if (value) {
            countProgress += valueInt
            _valueProgress.postValue(countProgress)
            if (countProgress > 100) {
                _showButton.postValue(true)
            }
        } else {
            countProgress -= valueInt
            _valueProgress.postValue(countProgress)
            if (countProgress < 100) {
                _showButton.postValue(false)
            }
        }
    }

    private fun hideButton() {
        _showButton.postValue(false)
    }

    private fun clearProgress() {
        _valueProgress.postValue(0)
        countProgress = 0
    }

    fun updateEditMode() {
        isEditMode.value?.let { currentEditMode ->
            val updatedEditMode = !currentEditMode
            _isEditMode.postValue(updatedEditMode)

            updateRecipes(updatedEditMode)
        }
    }

    fun clearSelectionMode(clear:Boolean){
       updateRecipes(!clear)
        _isEditMode.postValue(!clear)
    }

    private fun updateRecipes(isSelectionMode: Boolean = true) {
        val recipes = _recipes.value?.map { recipe ->

            Recipe(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients,
                image = recipe.image,
                video = recipe.video,
                mainImage = recipe.mainImage,
                isSelectionMode = isSelectionMode
            )
        } ?: emptyList()

        clearSelectedRecipes()
        hideButton()
        clearProgress()
        _recipes.postValue(recipes)
    }

    private fun clearSelectedRecipes() {
        selectedChipStates.forEach { it.recipeId = null }
    }

    fun updateChipState(recipeId: Int, weekDay: WEEK, selectedState: Boolean) {
        val updatedSelectedState = !selectedState
        getValueProgress(updatedSelectedState)

        selectedChipStates.first { it.weekDay == weekDay }.recipeId =
            if (updatedSelectedState) recipeId else null

        val recipes = _recipes.value?.map { recipe ->

            val updatedChipStates = selectedChipStates.mapIndexed { index, selectedChipState ->

                val recipeChipState = recipe.dayOfWeekSelectedPair[index]
                selectedChipState.recipeId?.let { selectedId ->

                    if (selectedId == recipe.id) {
                        createDetailsList(recipe, weekDay)
                        ChipState(
                            id = recipeChipState.id,
                            isActive = !recipeChipState.isActive,
                            isVisible = true,
                            dayOfWeek = recipeChipState.dayOfWeek
                        )

                    } else {
                        ChipState(
                            id = recipeChipState.id,
                            isActive = false,
                            isVisible = false,
                            dayOfWeek = recipeChipState.dayOfWeek
                        )

                    }

                } ?: ChipState(id = recipeChipState.id, dayOfWeek = recipeChipState.dayOfWeek)
            }

            Recipe(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients,
                image = recipe.image,
                video = recipe.video,
                mainImage = recipe.mainImage,
                isSelectionMode = true,
                dayOfWeekSelectedPair = updatedChipStates
            )
        } ?: emptyList()

        _recipes.postValue(recipes)
    }

    private fun getDay(day: Int): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.SUNDAY
        val dayWeek = calendar[Calendar.DAY_OF_WEEK]
        when (day) {
            0 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.MONDAY - dayWeek)
            }
            1 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.TUESDAY - dayWeek)
            }
            2 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.WEDNESDAY - dayWeek)
            }
            3 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.THURSDAY - dayWeek)
            }
            4 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.FRIDAY - dayWeek)
            }
            5 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - dayWeek)
            }
            6 -> {
                calendar.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - dayWeek)
            }
        }
        return dateFormat.format(calendar.time)
    }

    private fun createDetailsList(
        recipe: Recipe,
        dayOfWeek: WEEK
    ) {
        val details = DetailsRecipes(
            id = recipe.id,
            name = recipe.name,
            description = recipe.description,
            image = recipe.image,
            dayOfWeek = dayOfWeek,
            day = setDay(dayOfWeek),
            ingredients = recipe.ingredients
        )
        _addDetailsRecipes.postValue(details)
    }

    private fun setDay(dayOfWeek: WEEK): String {
        return getDay(dayOfWeek.id)
    }
}


data class SelectedChip(val weekDay: WEEK, var recipeId: Int? = null)