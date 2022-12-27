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
import dk.kriaactividade.mealngram.presentation.utils.*
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

        observerResultNavigaton()

        return binding.root
    }

    private fun observerResultNavigaton() {
        val result = getNavigationResult("RESULT")
        result?.observe(viewLifecycleOwner) {
            when (it) {
                viewModel.getWeekNumber(0).getWeekNumber() -> {
                    binding.apply {
                        addRecipesInFirstWeek.gone()
                        goForRecipesInFirstWeek.visible()
                    }
                }
                viewModel.getWeekNumber(1).getWeekNumber() -> {
                    binding.apply {
                        addRecipesInSecondWeek.gone()
                        goForRecipesInSecondWeek.visible()
                    }
                }
                viewModel.getWeekNumber(2).getWeekNumber() -> {
                    binding.apply {
                        addRecipesInThirdWeek.gone()
                        goForRecipesInThirdWeek.visible()
                    }
                }
                viewModel.getWeekNumber(3).getWeekNumber() -> {
                    binding.apply {
                        addRecipesInFourWeek.gone()
                        goForRecipesInFourWeek.visible()
                    }
                }
            }
        }
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
//            viewModel.getRoomList().map {
//                if (it.weekNumber == viewModel.getWeekNumber(0).getWeekNumber()){
//                    binding.apply {
//                        addRecipesInFirstWeek.gone()
//                        goForRecipesInFirstWeek.visible()
//                    }
//                }
//                if (it.weekNumber == viewModel.getWeekNumber(1).getWeekNumber()){
//                    binding.apply {
//                        addRecipesInSecondWeek.gone()
//                        goForRecipesInSecondWeek.visible()
//                    }
//                }
//                if (it.weekNumber == viewModel.getWeekNumber(2).getWeekNumber()){
//                    binding.apply {
//                        addRecipesInThirdWeek.gone()
//                        goForRecipesInThirdWeek.visible()
//                    }
//                }
//                if (it.weekNumber == viewModel.getWeekNumber(3).getWeekNumber()){
//                    binding.apply {
//                        addRecipesInFourWeek.gone()
//                        goForRecipesInFourWeek.visible()
//                    }
//                }
//            }
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

