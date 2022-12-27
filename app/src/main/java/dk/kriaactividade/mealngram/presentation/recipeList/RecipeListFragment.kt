package dk.kriaactividade.mealngram.presentation.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRecipeListBinding
import dk.kriaactividade.mealngram.presentation.utils.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeListBinding.inflate(layoutInflater)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        val recipesAdapter = RecipeListAdapter(requireContext(), viewModel)

        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipesAdapter
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    RecipeListUiState.Error -> {}
                    RecipeListUiState.Loading -> binding.loading.visible()
                    is RecipeListUiState.Success -> {
                        binding.loading.gone()
                        binding.layoutRecipes.visible()
                        recipesAdapter.submitList(uiState.uiData.recipes)

                        binding.progress.progress = uiState.uiData.progressValue
                        binding.buttonOk.isVisible = uiState.uiData.showButton

                    }
                    is RecipeListUiState.CompleteSelection -> {

                        binding.buttonOk.setOnClickListener {
                            setNavigationResult(viewModel.getArgsDateLong().getWeekNumber(),"RESULT")
                            //recipeWeekRepository.insertListWeek(uiState.complete.completeSelection)
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }

        return binding.root
    }
}