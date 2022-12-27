package dk.kriaactividade.mealngram.presentation.recipeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.data.domain.RecipesDetails
import dk.kriaactividade.mealngram.database.room.RecipeRoomWeekItem
import dk.kriaactividade.mealngram.databinding.FragmentRecipeDetailsBinding
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeItem
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListUiData
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListUiState
import dk.kriaactividade.mealngram.presentation.utils.Constants.RESULT_FROM_DETAILS
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private val args: RecipeDetailsFragmentArgs by navArgs()
    private lateinit var binding : FragmentRecipeDetailsBinding
    @Inject
    lateinit var viewModel:RecipeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater)
        lifecycleScope.launch {
            setViewPager(viewModel.getDetailsList(args.weekNumber))
        }

        binding.apply {
            loading.gone()
            layoutMyRecipes.visible()
            toolbarRecipesDetails.also {
                it.buttonBack.visible()
            }
        }


        configureToolbar()
        return binding.root
    }

    private fun backToRecipeList(){
        findNavController().navigateUp()
    }

    private fun configureToolbar() {
        binding.toolbarRecipesDetails.apply {
            textToolbar.text = getString(R.string.title_dashboard)
            buttonBack.setOnClickListener {
                backToRecipeList()
            }
        }
    }

    private fun setViewPager(listRecipes: List<RecipeRoomWeekItem>){
        binding.vpMyRecipes.adapter = RecipesSelectedViewPagerAdapter(requireContext(),
            listRecipes
        )
        binding.indicator.setupWithViewPager(binding.vpMyRecipes,false)
    }
}