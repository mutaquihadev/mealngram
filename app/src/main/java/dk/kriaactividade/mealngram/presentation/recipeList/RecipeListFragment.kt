package dk.kriaactividade.mealngram.presentation.recipeList

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.database.room.RoomViewModel
import dk.kriaactividade.mealngram.databinding.FragmentRecipeListBinding
import dk.kriaactividade.mealngram.databinding.LayoutBottonSheetDialogBinding
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private lateinit var binding: FragmentRecipeListBinding

    @Inject
    lateinit var recipesViewModel: RecipeListViewModel

    @Inject
    lateinit var roomViewModel: RoomViewModel
    private val recipeListAdapter by lazy {
        RecipeListAdapter(requireContext() , recipesViewModel::onChipSelected)
    }
    private var listDetails = mutableListOf<DetailsRecipes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //setupObservers()
        //setupAdapter()
//        binding.fabAdd.setOnClickListener { recipesViewModel.updateEditMode() }
//        binding.buttonOk.setOnClickListener {
//            val currentBackStackEntry = findNavController().currentBackStackEntry
//            val savedStateHandle = currentBackStackEntry?.savedStateHandle
//            savedStateHandle?.getLiveData<Boolean>(RESULT_FROM_DETAILS)
//                ?.observe(currentBackStackEntry, Observer { result ->
//                    recipesViewModel.clearSelectionMode(result)
//                })
//            findNavController().navigate(RecipeListFragmentDirections.goToMyRecipes(listDetails.toTypedArray()))
//        }
        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeListAdapter
        }

        binding.fabAdd.setOnClickListener { recipesViewModel.updateEditMode() }

        lifecycleScope.launch {
            recipesViewModel.uiState.collect { uiState ->
                when (uiState) {
                    RecipeListUiState.Error -> {

                    }
                    RecipeListUiState.Loading -> binding.loading.visible()

                    is RecipeListUiState.Success -> {
                        binding.loading.gone()
                        binding.layoutRecipes.visible()
                        recipeListAdapter.submitList(uiState.uiData.recipes)

                        binding.progress.isVisible = uiState.uiData.showProgress
                        binding.progress.progress = uiState.uiData.progressValue
                        binding.buttonOk.isVisible = uiState.uiData.showButton

                    }
                }
            }
        }

        return binding.root
    }

    private fun setupObservers() {
//        observerProgress()
//        observerRecipes()
//        observerEditMode()
//        observerButton()
//        observerDetailsList()
    }

    private fun observerEditMode() {
//        recipesViewModel.isEditMode.observe(viewLifecycleOwner) { isEditMode ->
//            animateFab(binding.fabAdd, isEditMode)
//            binding.progress.isVisible = isEditMode
//        }
    }

//    private fun observerRecipes() {
//        recipesViewModel.recipes.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                recipeListAdapter.submitList(it)
//                binding.apply {
//                    loading.gone()
//                    layoutRecipes.visible()
//                }
//            }
//            saveInCache(it)
//        }
//    }
//
//    private fun saveInCache(recipeList: List<Recipe>) {
//        roomViewModel.allPerson.observe(viewLifecycleOwner) {
//            if (it.isEmpty()) {
//                roomViewModel.insertList(recipeList)
//            }
//        }
//
//    }
//
//    private fun observerProgress() {
//        recipesViewModel.valueProgress.observe(viewLifecycleOwner) {
//            binding.progress.progress = it
//        }
//    }
//
//    private fun observerButton() {
//        recipesViewModel.showButton.observe(viewLifecycleOwner) {
//            binding.buttonOk.isVisible = it
//        }
//    }
//
//    private fun observerDetailsList() {
//        recipesViewModel.addDetailsRecipes.observe(viewLifecycleOwner) { details ->
//            listDetails.add(details)
//        }
//    }
//
//    private fun animateFab(v: View, rotate: Boolean) {
//        v.animate().setDuration(200).setListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    super.onAnimationEnd(animation)
//                }
//            }).rotation(if (rotate) 135f else 0f)
//    }
//
//
//    private fun setupAdapter() {
//        binding.recyclerHome.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = recipeListAdapter
//        }
//    }
//
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
            val listImage = mutableListOf<String>()
            listImage.add(recipes.image)
            vpImageRecipes.adapter = DetailsRecipesViewPageAdapter(requireContext(), listImage)
            indicator.setupWithViewPager(vpImageRecipes, true)
        }
        dialog.show()
    }
}


