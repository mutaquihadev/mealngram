package dk.kriaactividade.mealngram.presentation.favorite.selectFavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentSelectFavoriteRecipesBinding
@AndroidEntryPoint
class SelectFavoriteRecipesFragment : Fragment() {
    private lateinit var binding: FragmentSelectFavoriteRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectFavoriteRecipesBinding.inflate(layoutInflater)

        return binding.root
    }

}