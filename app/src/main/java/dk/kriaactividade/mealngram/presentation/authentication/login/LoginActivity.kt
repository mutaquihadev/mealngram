package dk.kriaactividade.mealngram.presentation.authentication.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dk.kriaactividade.mealngram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}