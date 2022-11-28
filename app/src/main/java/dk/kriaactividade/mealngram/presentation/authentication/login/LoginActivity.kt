package dk.kriaactividade.mealngram.presentation.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.MainActivity
import dk.kriaactividade.mealngram.databinding.ActivityLoginBinding
import dk.kriaactividade.mealngram.presentation.authentication.register.RegisterActivity
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToRegister()
        goToHome()
        observerLogin()
    }

    private fun observerLogin() {
        viewModel.loginSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Nã foi possivel logar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun goToHome() {
        binding.btnLogin.setOnClickListener {
            binding.apply {
                viewModel.login(this@LoginActivity,editEmailLogin.text.toString(),
                    editPasswordLogin.text.toString())
            }
        }
    }

    private fun goToRegister() {
        binding.btnRegisterInLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}