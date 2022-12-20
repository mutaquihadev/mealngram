package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRecipesSelectedBinding
import dk.kriaactividade.mealngram.presentation.utils.formatDateForLiteral
import dk.kriaactividade.mealngram.presentation.utils.gone
import dk.kriaactividade.mealngram.presentation.utils.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesSelectedFragment : Fragment() {
    private lateinit var binding: FragmentRecipesSelectedBinding

    @Inject
    lateinit var viewModel: RecipesSelectedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesSelectedBinding.inflate(layoutInflater)

        viewModel.getCurrentWeek()

        observerListDateWeek()

        return binding.root
    }

    private fun observerListDateWeek() {
        viewModel.listDateWeek.observe(viewLifecycleOwner) {hasMapDate ->
            binding.apply {
                firstWeek.text = "PRIMEIRA SEMANA"
                secondWeek.text = "PROXIMA SEMANA"

                hasMapDate[2].keys.map { initialDate ->
                    hasMapDate[2].values.map { finalDate ->
                        thirdWeek.text =
                            "${initialDate.formatDateForLiteral()} á ${finalDate.formatDateForLiteral()}"
                    }
                }

                hasMapDate[3].keys.map { initialDate ->
                    hasMapDate[3].values.map { finalDate ->
                        fourWeek.text =
                            "${initialDate.formatDateForLiteral()} á ${finalDate.formatDateForLiteral()}"
                    }
                }
                addRecipesInFirstWeek.setOnClickListener {
                    findNavController().navigate(RecipesSelectedFragmentDirections.actionNavigationDashboardToNavigationHome())
                }
            }
        }
    }
}

