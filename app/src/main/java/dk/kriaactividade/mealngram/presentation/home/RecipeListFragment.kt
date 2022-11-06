package dk.kriaactividade.mealngram.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.databinding.FragmentRecipeListBinding
import dk.kriaactividade.mealngram.databinding.LayoutBottonSheetDialogBinding
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private lateinit var binding: FragmentRecipeListBinding

    @Inject
    lateinit var recipesViewModel: RecipeListViewModel
    private val recipeListAdapter by lazy {
        RecipeListAdapter(requireContext(), ::getDetailsRecipes)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

        setupObservers()
        setupAdapter()
        binding.fabAdd.setOnClickListener { recipesViewModel.updateEditMode() }

        return binding.root
    }

    private fun setupObservers() {
        observerProgress()
        observerRecipes()
        observerEditMode()
    }

    private fun observerEditMode() {
        recipesViewModel.isEditMode.observe(viewLifecycleOwner) { isEditMode ->
            animateFab(binding.fabAdd, isEditMode)
            binding.progress.isVisible = isEditMode
        }
    }

    private fun observerRecipes() {
        recipesViewModel.recipes.observe(viewLifecycleOwner) {
            recipeListAdapter.submitList(it)
            binding.apply {
                loading.gone()
                layoutRecipes.visible()
            }
        }
    }

    private fun observerProgress() {
        recipesViewModel.valueProgress.observe(viewLifecycleOwner) {
            binding.progress.progress = it
            if (it >= 100) {
                binding.buttonOk.visible()
                binding.buttonOk.setOnClickListener {

                }
            } else {
                binding.buttonOk.gone()
            }
        }
    }

    private fun animateFab(v: View, rotate: Boolean) {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .rotation(if (rotate) 135f else 0f)
    }


    private fun setupAdapter() {
        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeListAdapter
        }
    }

    private fun getDetailsRecipes(recipes: Recipe) {
        val dialog = BottomSheetDialog(requireContext())
        val binding = LayoutBottonSheetDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        binding.apply {
            titleRecipe.text = recipes.name
            descriptionDetails.text = recipes.description
            for (ingredient in 0 until recipes.ingredients.size) {
                val chip = Chip(requireContext())
                chipGroupRecipes.addView(chip)
                chip.text = recipes.ingredients[ingredient]
            }
            vpImageRecipes.adapter =
                DetailsRecipesViewPageAdapter(requireContext(), recipes.images)
            indicator.setupWithViewPager(vpImageRecipes, true)
        }
        dialog.show()
    }
}