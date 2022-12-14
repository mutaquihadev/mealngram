package dk.kriaactividade.mealngram.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentFavoriteBinding
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private lateinit var binding:FragmentFavoriteBinding
    @Inject
    lateinit var viewModel: FavoriteRecipesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        val favoriteRecipesAdapter = FavoriteRecipesAdapter()
        binding.rvRecipesFavorites.apply {
            adapter = favoriteRecipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.toolbarFavorite.apply {
            textToolbar.text = getString(R.string.favorite)
            buttonAddFavorite.visible()
            buttonAddFavorite.setOnClickListener {
                findNavController().navigate(FavoriteRecipesFragmentDirections.actionNavigationFavoriteToNavigationSelectFavoriteRecipes())
            }
        }

        viewModel.getFavorite.observe(viewLifecycleOwner){
            favoriteRecipesAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleGetAllRecipes()
    }
}