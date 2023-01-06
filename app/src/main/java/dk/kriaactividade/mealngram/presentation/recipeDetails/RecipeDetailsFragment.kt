package dk.kriaactividade.mealngram.presentation.recipeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.database.room.SelectableRecipe
import dk.kriaactividade.mealngram.databinding.FragmentRecipeDetailsBinding
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailsBinding
    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater)

        binding.apply {
            loading.gone()
            layoutMyRecipes.visible()
            toolbarRecipesDetails.also {
                it.buttonBack.visible()
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { recipes -> setViewPager(recipes) }
        }

        configureToolbar()
        return binding.root
    }

    private fun backToRecipeList() {
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

    private fun setViewPager(listRecipes: List<SelectableRecipe>) {
        binding.vpMyRecipes.adapter = RecipesSelectedViewPagerAdapter(
            requireContext(),
            listRecipes
        )
        binding.indicator.setupWithViewPager(binding.vpMyRecipes, false)
    }
}