package dk.kriaactividade.mealngram.presentation.recipeDetails

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.MainActivity
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.databinding.FragmentRecipeDetailsBinding
import dk.kriaactividade.mealngram.presentation.utils.Constants.RESULT_FROM_DETAILS
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {
    val args: RecipeDetailsFragmentArgs by navArgs()
    private lateinit var binding : FragmentRecipeDetailsBinding
    @Inject
    lateinit var viewModel:RecipeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater)
        setViewPager(args.detailsRecipes.toList())
        binding.apply {
            loading.gone()
            layoutMyRecipes.visible()
            toolbarRecipesDetails.also {
                it.buttonBack.visible()
            }
        }

        binding.btnToFinish.setOnClickListener {
            viewModel.setDetailsList(args.detailsRecipes.toList())
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(RESULT_FROM_DETAILS, true)
            findNavController().navigateUp()
        }

        configureToolbar()
        return binding.root
    }

    private fun configureToolbar() {
        binding.toolbarRecipesDetails.apply {
            textToolbar.text = getString(R.string.title_dashboard)
            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setViewPager(listRecipes: List<DetailsRecipes>){
        binding.vpMyRecipes.adapter = RecipesSelectedViewPagerAdapter(requireContext(),
            listRecipes
        )
        binding.indicator.setupWithViewPager(binding.vpMyRecipes,false)
    }
}