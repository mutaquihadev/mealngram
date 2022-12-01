package dk.kriaactividade.mealngram.presentation.authentication.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.FragmentRegisterBinding
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding


    @Inject
    lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        openDataPicker()
        observerBirthday()
        observerEmail()
        observerPassword()
        confirmRegister()
        observerRegister()

        return binding.root
    }

    private fun confirmRegister() {
        binding.btnRegister.setOnClickListener {
            binding.apply {
                viewModel.validateEmail(editEmail.text.toString())
                viewModel.confirmPassword(
                    editPassword.text.toString(),
                    editConfirmPassword.text.toString()
                )
                register()
            }
        }
    }

    private fun register() {
        viewModel.isPassword.value?.let { password ->
            viewModel.isErrorEmail.value?.let { email ->
                if (password && email) {
                    binding.apply {
                        activity?.let {
                            viewModel.registerUser(
                                it,editEmail.text.toString(),
                                editPassword.text.toString())
                        }
                    }
                }
            }
        }
    }

    private fun observerRegister(){
        viewModel.successRegister.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigateUp()
            }else{
                Toast.makeText(requireContext(), "Falha ao registrar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerPassword() {
        viewModel.isPassword.observe(viewLifecycleOwner) {
            binding.apply {
                if (it) {
                    inputPassword.isErrorEnabled = false
                    inputPassword.error = ""
                    inputConfirmPassword.isErrorEnabled = false
                    inputConfirmPassword.error = ""
                } else {
                    inputPassword.isErrorEnabled = true
                    inputPassword.error = "Suas senhas não conferem"
                    inputConfirmPassword.isErrorEnabled = true
                    inputConfirmPassword.error = "Suas senhas não conferem"
                }
            }
        }
    }

    private fun observerEmail() {
        viewModel.isErrorEmail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it) {
                    inputEmail.isErrorEnabled = false
                    inputEmail.error = ""
                } else {
                    inputEmail.isErrorEnabled = true
                    inputEmail.error = "E-mail inválido"
                }

            }
        }
    }

    private fun observerBirthday() {
        viewModel.birthday.observe(viewLifecycleOwner) {
            binding.editBirthday.setText(it)
        }
    }

    private fun openDataPicker() {
        binding.btnCalendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    viewModel.getBirthday(day, month, year)
                }, viewModel.getYear(), viewModel.getMonth(), viewModel.getDay()
            )
            datePickerDialog.show()
        }
    }
}