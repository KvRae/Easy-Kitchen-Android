package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.view.EditProfileActivity
import androidx.appcompat.widget.Toolbar
import devsec.app.easykitchen.ui.main.view.LoginActivity
import devsec.app.easykitchen.utils.session.SessionPref

class ProfileFragment : Fragment() {
    lateinit var session : SessionPref


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionPref(activity?.applicationContext!!)
        val username = view.findViewById<TextView>(R.id.profileUsernameINPT)
        val email = view.findViewById<TextView>(R.id.profileEmailINPT)
        val phone = view.findViewById<TextView>(R.id.profilePhoneINPT)
        session.checkLogin()

        var user : HashMap<String, String> = session.getUserPref()
        username.text = user.get(SessionPref.USER_NAME)
        email.text = user.get(SessionPref.USER_EMAIL)
        phone.text = user.get(SessionPref.USER_PHONE)


         val toolbar = view.findViewById<Toolbar>(R.id.profileToolbar)
         toolbar.menu.findItem(R.id.editProfile).setOnMenuItemClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
            true
        }
        toolbar.menu.findItem(R.id.logoutProfile).setOnMenuItemClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            true
        }

        toolbar.menu.findItem(R.id.logoutProfile).setOnMenuItemClickListener {
            session.logoutUser()
            true
        }

    }
    fun deleteAccountFunction(){
    }
}