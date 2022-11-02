package dk.kriaactividade.mealngram.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.projectx.utils.Extension.gone
import com.example.projectx.utils.Extension.visible
import com.example.projectx.utils.ViewAnimation
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentHomeBinding
import dk.kriaactividade.mealngram.databinding.LayoutBottonSheetDialogBinding
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import dk.kriaactividade.mealngram.presentation.utils.Observable
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var isRotate: Boolean = false
    private lateinit var homeAdapter: HomeAdapter

    @Inject
    lateinit var recipesViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setupEditMode()
        observeProgress()
        observerDayOfWeekMark()
        observeChip()
        recipesViewModel.getRecipesList()
        observerRequest()
        return binding.root
    }

    private fun observerRequest(){
        recipesViewModel.recipes.observe(viewLifecycleOwner){
            setupAdapter(it)
            binding.apply {
                loading.gone()
                layoutRecipes.visible()
            }
        }
    }

    private fun observeProgress() {
        Observable.valueProgress.observe(viewLifecycleOwner) {
            binding.progress.progress = it
            if(it >= 100){
                binding.buttonOk.visible()
            }else{
                binding.buttonOk.gone()
            }
        }
    }

    private fun observeChip(){
        Observable.isActive.observe(viewLifecycleOwner) {
            homeAdapter.verifyChip(it)
        }
    }

    private fun observerDayOfWeekMark(){
        Observable.verifyDayOfWeek.observe(viewLifecycleOwner) {
           homeAdapter.dayCheck(it)
        }
    }

    private fun setupEditMode() {
        binding.fabAdd.setOnClickListener {
            isRotate = ViewAnimation.rotateFab(it, !isRotate)
            if (isRotate) {
                homeAdapter.openMark(true)
                binding.progress.visible()
            } else {
                homeAdapter.openMark(false)
                binding.progress.gone()
            }
        }
    }


    private fun setupAdapter(listRecipes:MutableList<RecipesResponse>) {
        binding.recyclerHome.apply {
            homeAdapter = HomeAdapter(context,listRecipes,getDetailsRecipes())
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
            homeAdapter.submitList(listRecipes)
        }
    }

    private fun getDetailsRecipes() : ClickToDetailsRecipes{
        return object : ClickToDetailsRecipes{
            override fun detailsRecipes(recipesResponse: RecipesResponse) {
                val dialog = BottomSheetDialog(requireContext())
                val binding = LayoutBottonSheetDialogBinding.inflate(layoutInflater)
                dialog.setContentView(binding.root)
                binding.apply {
                    titleRecipe.text = recipesResponse.name
                    vpImageRecipes.adapter = DetailsRecipesViewPageAdapter(requireContext(),recipesResponse.imagesUrl)
                    indicator.setupWithViewPager(vpImageRecipes,true)
                    closeButton.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.setCancelable(false)
                dialog.show()
            }

        }
    }
}