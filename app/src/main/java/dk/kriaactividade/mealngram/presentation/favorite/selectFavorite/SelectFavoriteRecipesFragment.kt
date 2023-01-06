package dk.kriaactividade.mealngram.presentation.favorite.selectFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentSelectFavoriteRecipesBinding
import dk.kriaactividade.mealngram.entities.ui.selectfavoriterecipe.SelectFavoriteUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SelectFavoriteRecipesFragment : Fragment() {
    private lateinit var binding: FragmentSelectFavoriteRecipesBinding
    @Inject
    lateinit var viewModel: SelectFavoriteRecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectFavoriteRecipesBinding.inflate(layoutInflater)

        val selectFavoriteRecipesAdapter = SelectFavoriteRecipesAdapter(viewModel)

        binding.rvSelectFavoriteRecipes.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
            adapter = selectFavoriteRecipesAdapter
        }

        lifecycleScope.launch{
            viewModel.uiState.collect {uiState ->
                when(uiState){
                    is SelectFavoriteUiState.Success -> {
                        selectFavoriteRecipesAdapter.submitList(uiState.uiData.selectedFavorites)
                    }
                }

            }
        }
        return binding.root
    }



}