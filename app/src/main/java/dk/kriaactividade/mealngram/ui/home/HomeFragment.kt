package dk.kriaactividade.mealngram.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.utils.Extension.gone
import com.example.projectx.utils.Extension.visible
import com.example.projectx.utils.ViewAnimation
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentHomeBinding
import dk.kriaactividade.mealngram.ui.FoodModel
import dk.kriaactividade.mealngram.utils.Observable

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var isRotate: Boolean = false
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setupAdapter()
        setupEditMode()
        observeProgress()
        observerDayOfWeekMark()
        observeChip()
        return binding.root
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

    private fun mockList():MutableList<FoodModel> {
        val foodList = mutableListOf<FoodModel>()
        foodList.add(FoodModel(0, getString(R.string.text_lorem_ipsum)))
        foodList.add(FoodModel(1, getString(R.string.text_lorem_ipsum)))
        return foodList
    }

    private fun setupAdapter() {
        binding.recyclerHome.apply {
            homeAdapter = HomeAdapter(context,mockList())
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
            homeAdapter.submitList(mockList())
        }
    }

}