package dk.kriaactividade.mealngram.presentation.authentication.register

import android.app.DatePickerDialog
import android.os.Bundle
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