package devsec.app.easykitchen.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.databinding.ActivityMainMenuBinding
import devsec.app.easykitchen.ui.main.fragments.*
import devsec.app.easykitchen.utils.services.Notification

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationView = findViewById<NavigationView>(R.id.nav_view)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (intent.getStringExtra("fragment") != null) {
            replaceFragment(BasketFragment())
        }else{replaceFragment(HomeFragment())}


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.food -> replaceFragment(FoodFragment())
                R.id.blog -> replaceFragment(BlogFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.cart -> replaceFragment(BasketFragment())

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