package devsec.app.easykitchen.ui.main.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.view.EditProfileActivity
import androidx.appcompat.widget.Toolbar
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.ui.main.view.LoginActivity
import devsec.app.easykitchen.utils.services.LoadingDialog
import devsec.app.easykitchen.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val loadingDialog = LoadingDialog(this.requireActivity())

        val username = view.findViewById<TextView>(R.id.profileUsernameINPT)
        val email = view.findViewById<TextView>(R.id.profileEmailINPT)
        val phone = view.findViewById<TextView>(R.id.profilePhoneINPT)
        val deleteButton = view.findViewById<TextView>(R.id.deleteAccountBTN)
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

        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Delete Account")
            builder.setMessage("Are you sure you want to delete your account?")
            builder.setPositiveButton("Yes") { dialog, which ->
                loadingDialog.startLoadingDialog()
                val apiService = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
                val call = apiService.deleteUser(session.getUserPref().get(SessionPref.USER_ID).toString())
                call.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            loadingDialog.dismissDialog()
                            Toast.makeText(activity?.applicationContext, "Account deleted", Toast.LENGTH_SHORT).show()
                            session.logoutUser()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        loadingDialog.dismissDialog()
                        Toast.makeText(activity?.applicationContext, "Error deleting account", Toast.LENGTH_SHORT).show()
                    }
                })
                Toast.makeText(activity?.applicationContext, "Account deleted", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(activity?.applicationContext, "Account not deleted", Toast.LENGTH_SHORT).show()
            }
            builder.show()

        }
    }
}