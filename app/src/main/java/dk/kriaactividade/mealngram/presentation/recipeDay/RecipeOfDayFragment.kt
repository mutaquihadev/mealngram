package dk.kriaactividade.mealngram.presentation.recipeDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRecipeOfDayBinding
import dk.kriaactividade.mealngram.presentation.recipeList.toWeek
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeOfDayFragment : Fragment() {

    private lateinit var binding: FragmentRecipeOfDayBinding

    private val viewModel: RecipeOfDayViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeOfDayBinding.inflate(layoutInflater)

        lifecycleScope.launch{
            viewModel.uiState.collect { uiState ->
                when(uiState){
                is RecipeOfDayUiState.Success -> {
                    binding.apply {
                        toolbarRecipeOfDay.textToolbar.text = uiState.uiData.selectedRecipe.dateWeek.toWeek().name
                        imageRecipeOfDay.load(uiState.uiData.selectedRecipe.image)
                        textRecipeOfDay.text = uiState.uiData.selectedRecipe.name
                        textDescriptionRecipeofDay.text = uiState.uiData.selectedRecipe.description
                    }
                }
                }
            }
        }

        return binding.root
    }

}