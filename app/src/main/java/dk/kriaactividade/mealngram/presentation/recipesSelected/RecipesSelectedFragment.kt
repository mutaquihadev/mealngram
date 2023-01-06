package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentRecipesSelectedBinding
import dk.kriaactividade.mealngram.entities.ui.recipelistdetails.RecipeListDetailsUiState
import dk.kriaactividade.mealngram.presentation.utils.getNavigationResult
import dk.kriaactividade.mealngram.presentation.utils.getWeekNumber
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesSelectedFragment : Fragment() {
    private lateinit var binding: FragmentRecipesSelectedBinding

    private val viewModel: RecipesSelectedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesSelectedBinding.inflate(layoutInflater)

        observerListDateWeek()

        verifyIfListIsEmpty()
        addRecipeWeek()
        goToRecipesSelectedInWeek()

        return binding.root
    }

    private fun observerListDateWeek() {
        viewModel.listDateWeek.observe(viewLifecycleOwner) { pairDate ->
            binding.apply {
                firstWeek.text = getString(R.string.first_week)
                secondWeek.text = getString(R.string.next_week)

                thirdWeek.text =
                    viewModel.convertDateForString(pairDate[2].first, pairDate[2].second)
                fourWeek.text =
                    viewModel.convertDateForString(pairDate[3].first, pairDate[3].second)

            }
        }
    }

    private fun verifyIfListIsEmpty() {
        lifecycleScope.launch {
           viewModel.uiState.collect {
               when(it){
                   is RecipeListDetailsUiState.Success -> {
                       it.recipes.map { selectableRecipe ->
                           if (selectableRecipe.weekNumber == viewModel.getWeekNumber(0).getWeekNumber()){
                               binding.apply {
                                   addRecipesInFirstWeek.gone()
                                   goForRecipesInFirstWeek.visible()
                               }
                           }
                           if (selectableRecipe.weekNumber == viewModel.getWeekNumber(1).getWeekNumber()){
                               binding.apply {
                                   addRecipesInSecondWeek.gone()
                                   goForRecipesInSecondWeek.visible()
                               }
                           }
                           if (selectableRecipe.weekNumber == viewModel.getWeekNumber(2).getWeekNumber()){
                               binding.apply {
                                   addRecipesInThirdWeek.gone()
                                   goForRecipesInThirdWeek.visible()
                               }
                           }
                           if (selectableRecipe.weekNumber == viewModel.getWeekNumber(3).getWeekNumber()){
                               binding.apply {
                                   addRecipesInFourWeek.gone()
                                   goForRecipesInFourWeek.visible()
                               }
                           }
                       }
                   }
               }
           }
        }
    }

    private fun addRecipeWeek() {
        binding.apply {
            addRecipesInFirstWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationHome(
                        viewModel.getWeekNumber(0)
                    )
                )
            }
            addRecipesInSecondWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationHome(
                        viewModel.getWeekNumber(1)
                    )
                )
            }
            addRecipesInThirdWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationHome(
                        viewModel.getWeekNumber(2)
                    )
                )
            }
            addRecipesInFourWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationHome(
                        viewModel.getWeekNumber(3)
                    )
                )
            }
        }
    }

    private fun goToRecipesSelectedInWeek() {
        binding.apply {
            goForRecipesInFirstWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationRecipeDetails(
                        viewModel.getWeekNumber(0).getWeekNumber()
                    )
                )
            }
            goForRecipesInSecondWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationRecipeDetails(
                        viewModel.getWeekNumber(1).getWeekNumber()
                    )
                )
            }
            goForRecipesInThirdWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationRecipeDetails(
                        viewModel.getWeekNumber(2).getWeekNumber()
                    )
                )
            }
            goForRecipesInFourWeek.setOnClickListener {
                findNavController().navigate(
                    RecipesSelectedFragmentDirections.actionNavigationRecipeSelectedToNavigationRecipeDetails(
                        viewModel.getWeekNumber(3).getWeekNumber()
                    )
                )
            }
        }
    }
}

