package dk.kriaactividade.mealngram.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentDashboardBinding
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    @Inject
    lateinit var viewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        viewModel.myRecipes()
        viewModel.myRecipes.observe(viewLifecycleOwner){
            setViewPager(it)
        }

        return binding.root
    }

    private fun setViewPager(listRecipes: MutableList<RecipesResponse>){
        binding.vpMyRecipes.adapter = RecipesSelectedViewPagerAdapter(requireContext(),
            listRecipes
        )
        binding.indicator.setupWithViewPager(binding.vpMyRecipes,false)
    }

}

