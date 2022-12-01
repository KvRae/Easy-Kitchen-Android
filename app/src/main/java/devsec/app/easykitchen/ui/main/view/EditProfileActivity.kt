package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import devsec.app.easykitchen.R
import devsec.app.easykitchen.utils.session.SessionPref

class EditProfileActivity : AppCompatActivity() {
    lateinit var sessionPref: SessionPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        sessionPref = SessionPref(this)

        val username = findViewById<TextView>(R.id.editProfileUsername)
        val email = findViewById<TextView>(R.id.editProfileEmail)
        val phone = findViewById<TextView>(R.id.editProfilePhone)

        var user : HashMap<String, String> = sessionPref.getUserPref()
        username.text = user.get(SessionPref.USER_NAME)
        email.text = user.get(SessionPref.USER_EMAIL)
        phone.text = user.get(SessionPref.USER_PHONE)

        val toolbar = findViewById<Toolbar>(R.id.editProfileToolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}