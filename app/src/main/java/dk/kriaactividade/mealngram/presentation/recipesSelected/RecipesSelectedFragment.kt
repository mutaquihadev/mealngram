package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRecipesSelectedBinding
import javax.inject.Inject

@AndroidEntryPoint
class RecipesSelectedFragment : Fragment() {
    private lateinit var binding: FragmentRecipesSelectedBinding
    @Inject
    lateinit var viewModel: RecipesSelectedViewModel
    private val recipesSelectedAdapter: RecipesSelectedAdapter by lazy {
        RecipesSelectedAdapter( ::onShowList )
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesSelectedBinding.inflate(layoutInflater)
        setupAdapter()
        viewModel.recipesSelected.observe(viewLifecycleOwner){
            recipesSelectedAdapter.submitList(it)
        }

        return binding.root
    }

    private fun onShowList(isOpen:Boolean){
        viewModel.listIsOpen(isOpen)
    }

    private fun setupAdapter(){
        binding.rvRecipesSelected.apply {
            adapter = recipesSelectedAdapter
            layoutManager = GridLayoutManager(context,1)
        }
    }
}

