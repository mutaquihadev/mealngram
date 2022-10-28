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
    private var listFood = mutableListOf<FoodModel>()
    private var isRotate: Boolean = false
    private val homeAdapter by lazy {
        HomeAdapter(requireContext(), listFood)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        mockList(listFood)
        setupAdapter()
        setupEditMode()
        observeProgress()
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

    private fun mockList(foodList: MutableList<FoodModel>) {
        foodList.add(FoodModel(0, getString(R.string.text_lorem_ipsum)))
    }

    private fun setupAdapter() {
        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
            homeAdapter.submitList(listFood)
        }
    }

}