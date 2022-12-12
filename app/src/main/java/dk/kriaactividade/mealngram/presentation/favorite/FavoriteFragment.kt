package dk.kriaactividade.mealngram.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding:FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)





        return binding.root
    }
}