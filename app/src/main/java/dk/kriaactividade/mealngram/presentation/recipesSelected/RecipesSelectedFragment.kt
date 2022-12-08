package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRecipesSelectedBinding
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesSelectedFragment : Fragment() {
    private lateinit var binding: FragmentRecipesSelectedBinding
    @Inject
    lateinit var viewModel: RecipesSelectedViewModel
    private val recipesSelectedAdapter: RecipesSelectedAdapter by lazy {
        RecipesSelectedAdapter()
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesSelectedBinding.inflate(layoutInflater)

        binding.rvRecipesSelected.apply {
            adapter = recipesSelectedAdapter
            layoutManager = GridLayoutManager(context,1)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect{ uiState ->
                when(uiState){
                    is RecipeListDetailsUiState.Error -> {

                    }
                    is RecipeListDetailsUiState.Loading -> {
                        binding.loading.visible()
                    }
                    is RecipeListDetailsUiState.Success -> {
                        binding.loading.gone()
                        binding.rvRecipesSelected.visible()
                        recipesSelectedAdapter.submitList(uiState.uiData.recipes)
                    }
                }

            }
        }

        return binding.root
    }
}

