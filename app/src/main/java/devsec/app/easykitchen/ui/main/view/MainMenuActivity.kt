package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import devsec.app.easykitchen.R
import devsec.app.easykitchen.databinding.ActivityMainMenuBinding
import devsec.app.easykitchen.ui.main.fragments.BlogFragment
import devsec.app.easykitchen.ui.main.fragments.FoodFragment
import devsec.app.easykitchen.ui.main.fragments.HomeFragment
import devsec.app.easykitchen.ui.main.fragments.ProfileFragment

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.food -> replaceFragment(FoodFragment())
                R.id.blog -> replaceFragment(BlogFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> {}
            }
            true
        }
    }
     fun replaceFragment(fragment: Fragment) {
         val fragmentTransaction = supportFragmentManager.beginTransaction()
         fragmentTransaction.replace(R.id.fragments_container, fragment)
         fragmentTransaction.commit()
     }
}