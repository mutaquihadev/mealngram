package dk.kriaactividade.mealngram.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentFavoriteBinding
import dk.kriaactividade.mealngram.presentation.utils.visible

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private lateinit var binding:FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        binding.toolbarFavorite.apply {
            textToolbar.text = getString(R.string.favorite)
            buttonAddFavorite.visible()
            buttonAddFavorite.setOnClickListener {
                findNavController().navigate(FavoriteRecipesFragmentDirections.actionNavigationFavoriteToNavigationSelectFavoriteRecipes())
            }
        }



        return binding.root
    }
}