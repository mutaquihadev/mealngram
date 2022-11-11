package dk.kriaactividade.mealngram

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import dk.kriaactividade.mealngram.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var TAG = "FIRESTORE_DEBUG"
    private lateinit var binding: ActivityMainBinding
    var db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        db.collection("recipes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    Log.e(TAG, "${result.documents[0].data}")
                }
            }


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}