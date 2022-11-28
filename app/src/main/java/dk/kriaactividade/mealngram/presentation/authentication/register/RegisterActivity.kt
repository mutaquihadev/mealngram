package dk.kriaactividade.mealngram.presentation.authentication.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.ActivityRegisterBinding
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

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
                register()
            }
        }
    }

    private fun register() {
        viewModel.isPassword.value?.let { password ->
            viewModel.isErrorEmail.value?.let { email ->
                if (password && email) {
                    binding.apply {
                        auth.createUserWithEmailAndPassword(
                            editEmail.text.toString(),
                            editPassword.text.toString()
                        )
                            .addOnCompleteListener(this@RegisterActivity) { task ->
                                if (task.isSuccessful) {
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Não foi possivel registar",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                    }
                }
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