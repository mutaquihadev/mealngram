package dk.kriaactividade.mealngram.presentation.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.database.room.RoomViewModel
import dk.kriaactividade.mealngram.databinding.FragmentRecipeListBinding
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private lateinit var binding: FragmentRecipeListBinding

    @Inject
    lateinit var recipesViewModel: RecipeListViewModel

    @Inject
    lateinit var roomViewModel: RoomViewModel
    private val recipeListAdapter by lazy {
        RecipeListAdapter(requireContext() , recipesViewModel::onChipSelected)
    }
    private var listDetails = mutableListOf<DetailsRecipes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        binding.fabAdd.setOnClickListener { recipesViewModel.updateEditMode() }
//        binding.buttonOk.setOnClickListener {
//            val currentBackStackEntry = findNavController().currentBackStackEntry
//            val savedStateHandle = currentBackStackEntry?.savedStateHandle
//            savedStateHandle?.getLiveData<Boolean>(RESULT_FROM_DETAILS)
//                ?.observe(currentBackStackEntry, Observer { result ->
//                    recipesViewModel.clearSelectionMode(result)
//                })
//            findNavController().navigate(RecipeListFragmentDirections.goToMyRecipes(listDetails.toTypedArray()))
//        }
        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeListAdapter
        }

        binding.fabAdd.setOnClickListener { recipesViewModel.updateEditMode() }

        lifecycleScope.launch {
            recipesViewModel.uiState.collect { uiState ->
                when (uiState) {
                    RecipeListUiState.Error -> {

                    }
                    RecipeListUiState.Loading -> binding.loading.visible()

                    is RecipeListUiState.Success -> {
                        binding.loading.gone()
                        binding.layoutRecipes.visible()
                        recipeListAdapter.submitList(uiState.uiData.recipes)

                        binding.progress.isVisible = uiState.uiData.showProgress
                        binding.progress.progress = uiState.uiData.progressValue
                        binding.buttonOk.isVisible = uiState.uiData.showButton

                    }
                }
            }
        }

        return binding.root
    }
}


