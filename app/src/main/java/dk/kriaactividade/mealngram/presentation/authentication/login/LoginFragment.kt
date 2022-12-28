package dk.kriaactividade.mealngram.presentation.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentLoginBinding
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        goToRegister()
        goToHome()
        observerLogin()
        observerUserLogged()
        return binding.root
    }

    private fun observerLogin() {
        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            it.keys.map {isSuccess ->
                if (isSuccess) {
                    findNavController().navigate(R.id.navigation_recipe_of_day)
                } else {
                    Toast.makeText(
                        requireContext(),
                        it[isSuccess],
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun observerUserLogged(){
        viewModel.userLogged.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(R.id.navigation_recipe_of_day)
            }
        }
    }

    private fun goToHome() {
        binding.btnLogin.setOnClickListener {
            binding.apply {
                activity?.let { it1 ->
                    viewModel.loginSuccess(
                        it1,editEmailLogin.text.toString(),
                        editPasswordLogin.text.toString())
                }
            }
        }
    }

    private fun goToRegister() {
        binding.btnRegisterInLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationRegister())
        }
    }
}