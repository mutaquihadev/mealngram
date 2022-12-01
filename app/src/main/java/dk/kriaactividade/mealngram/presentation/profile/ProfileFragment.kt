package dk.kriaactividade.mealngram.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentProfileBinding
import dk.kriaactividade.mealngram.presentation.authentication.login.LoginActivity
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)


        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }


        viewModel.logoutSuccess.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                activity?.finish()
            } else {
                Toast.makeText(requireContext(), "Não foi possivel deslogar", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }
}