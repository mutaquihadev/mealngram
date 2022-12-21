package dk.kriaactividade.mealngram.presentation.recipeDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRecipeOfDayBinding
import dk.kriaactividade.mealngram.presentation.recipeList.toWeek
import javax.inject.Inject

@AndroidEntryPoint
class RecipeOfDayFragment : Fragment() {

    private lateinit var binding: FragmentRecipeOfDayBinding
    @Inject
    lateinit var viewModel: RecipeOfDayViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeOfDayBinding.inflate(layoutInflater)

        viewModel.currentRecipe.observe(viewLifecycleOwner){
            binding.apply {
                toolbarRecipeOfDay.textToolbar.text = it.dateWeek.toWeek().name
                imageRecipeOfDay.load(it.image)
                textRecipeOfDay.text = it.name
                textDescriptionRecipeofDay.text = it.description
            }
        }

        return binding.root
    }

}