package dk.kriaactividade.mealngram.presentation.authentication.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.ActivityRegisterBinding
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openDataPicker()
        observerBirthday()
        observerEmail()
        observerPassword()
        confirmRegister()
    }

    private fun confirmRegister() {
        binding.btnRegister.setOnClickListener {
            binding.apply {
                viewModel.validateEmail(editEmail.text.toString())
                viewModel.confirmPassword(
                    editPassword.text.toString(),
                    editConfirmPassword.text.toString()
                )
            }
        }
    }

    private fun observerPassword() {
        viewModel.isPassword.observe(this) {
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
        viewModel.isErrorEmail.observe(this) {
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
        viewModel.birthday.observe(this) {
            binding.editBirthday.setText(it)
        }
    }

    private fun openDataPicker() {
        binding.btnCalendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    viewModel.getBirthday(day, month, year)
                }, viewModel.getYear(), viewModel.getMonth(), viewModel.getDay()
            )
            datePickerDialog.show()
        }
    }
}