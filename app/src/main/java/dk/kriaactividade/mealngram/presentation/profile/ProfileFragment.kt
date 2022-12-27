package dk.kriaactividade.mealngram.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentProfileBinding
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

        binding.goToFavorites.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToNavigationFavorite())
        }

        binding.goToRecipesOfMonth.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToNavigationDashboard())
        }


        viewModel.logoutSuccess.observe(viewLifecycleOwner) { isLogged ->
            if (!isLogged) {
                findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToNavigationLogin())
            } else {
                Toast.makeText(requireContext(), "Não foi possivel deslogar", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }
}