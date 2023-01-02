package devsec.app.easykitchen.ui.main.view


import android.view.Menu

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.databinding.ActivityMainMenuBinding
import devsec.app.easykitchen.ui.main.fragments.*
import devsec.app.easykitchen.utils.services.LoadingDialog
import devsec.app.easykitchen.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var sideNav: Menu


    private lateinit var session : SessionPref
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog (this)

        // Drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val deleteitem = navigationView.menu.findItem(R.id.nav_delete_profile)
        deleteitem.actionView

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
            
                 R.id.nav_recettes -> {
                    val intent = Intent(this, MyRecipesActivity::class.java)
                    startActivity(intent)
                    true
                }
                
                R.id.nav_rating -> {
                    Toast.makeText(this, "Comming soon!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_feedback -> {
                    Toast.makeText(this, "Comming soon!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_website -> {
                    Toast.makeText(this, "Comming soon!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_discussion -> {
                    val url = "https://github.com/KvRae/Easy-Kitchen-Android/discussions"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                R.id.nav_gitRepo -> {
                    val url = "https://github.com/KvRae/Easy-Kitchen-Android#readme"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.nav_edit_profile -> {
                    val intent = Intent(this, EditProfileActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.nav_logout -> {
                    session = SessionPref(this)
                    session.logoutUser()
                    val intent = Intent(this, LoginActivity::class.java)
                    drawerLayout.closeDrawer(navigationView)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_delete_profile -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Delete Account")
                    builder.setMessage("Are you sure you want to delete your account?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        loadingDialog.startLoadingDialog()
                        deleteAccount()
                        Toast.makeText(applicationContext, "Account deleted", Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        Toast.makeText(applicationContext, "Account not deleted", Toast.LENGTH_SHORT).show()
                    }
                    builder.show()


                }
            }
            true
        }
        session = SessionPref(this.applicationContext)
        val username = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_title)
        val user = session.getUserPref()
        username.text = user.get(SessionPref.USER_NAME)






        // bottom navigation bar
        if (intent.getStringExtra("openFragment") == "BasketFragment") {
            binding.bottomNavigationView.selectedItemId = R.id.cart
            supportFragmentManager.beginTransaction().replace(R.id.fragments_container, BasketFragment())
                .commit()



        } else {
            supportFragmentManager.beginTransaction().replace(R.id.fragments_container, HomeFragment())
                .commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.food -> replaceFragment(FoodFragment())
                R.id.blog -> replaceFragment(BlogFragment())
                R.id.cart -> replaceFragment(BasketFragment())
            }
            true
        }
    }

    private fun deleteAccount() {
        val apiService = RetrofitInstance.getRetrofitInstance().create(
            RestApiService::class.java)
        val call = apiService.deleteUser(session.getUserPref().get(SessionPref.USER_ID).toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    loadingDialog.dismissDialog()
                    Toast.makeText( applicationContext, "Account deleted", Toast.LENGTH_SHORT).show()
                    session.logoutUser()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadingDialog.dismissDialog()
                Toast.makeText(applicationContext, "Error deleting account", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun replaceFragment(fragment: Fragment) {
         val fragmentTransaction = supportFragmentManager.beginTransaction()
         fragmentTransaction.replace(R.id.fragments_container, fragment)
         fragmentTransaction.commit()
     }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}