package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.view.EditProfileActivity
import androidx.appcompat.widget.Toolbar
import devsec.app.easykitchen.ui.main.view.LoginActivity

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }
    fun deleteAccountFunction(){
    }
}