package dk.kriaactividade.mealngram.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)



        return binding.root
    }
}