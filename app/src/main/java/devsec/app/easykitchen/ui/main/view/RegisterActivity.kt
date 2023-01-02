package devsec.app.easykitchen.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.User
import devsec.app.easykitchen.databinding.ActivityRegisterBinding
import devsec.app.easykitchen.utils.services.LoadingDialog
import devsec.app.easykitchen.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback


class RegisterActivity : AppCompatActivity() {
    val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    lateinit var loginbtn : Button
    lateinit var registerbtn : Button
    lateinit var sessionPref: SessionPref
    lateinit var loadingDialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loadingDialog = LoadingDialog(this)
        sessionPref = SessionPref(this)
        if (sessionPref.isLoggedIn()) {
            val intent = Intent(this, MainMenuActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        registerbtn = findViewById<Button>(R.id.RegisterBTN)

        registerbtn.setOnClickListener {
            val username = findViewById<EditText>(R.id.loginEditText)
            val password = findViewById<EditText>(R.id.passwordInputEditText)
            val verifPass = findViewById<EditText>(R.id.passwordInputEditText2)
            val email = findViewById<EditText>(R.id.emailEditText)
            val phone = findViewById<EditText>(R.id.phoneEditText)
            if (validateRegister(username, password, verifPass, email, phone)) {
                register(username.text.toString().trim(), password.text.toString().trim(), email.text.toString().trim(), phone.text.toString().trim())}
            }

        loginbtn = findViewById<Button>(R.id.loginBtn)

        loginbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateRegister(username: EditText, password: EditText, verifPass: EditText, email: EditText, phone: EditText): Boolean {
        if (username.text.trim().isEmpty() || password.text.trim().isEmpty() || verifPass.text.trim().isEmpty() || email.text.trim().isEmpty() || phone.text.trim().isEmpty()) {

            if (phone.text.isEmpty()) {
                phone.error = "Phone is required"
                phone.requestFocus()
            }

            if (email.text.isEmpty()) {
                email.error = "Email is required"
                email.requestFocus()

            }


            if (verifPass.text.isEmpty()) {
                verifPass.error = "Password does not match"
                verifPass.requestFocus()

            }

            if (password.text.isEmpty()) {
                password.error = "Password is required"
                password.requestFocus()

            }

            if (username.text.isEmpty()) {
                username.error = "Username is required"
                username.requestFocus()

            }

            return false
        }

        //Patterns // Regex // Length
        if (password.text.length < 6){
            password.error = "Password must be at least 6 characters"
            password.requestFocus()
            return false
        }

        if (password.text.toString() != verifPass.text.toString()){
            verifPass.error = "Password does not match"
            verifPass.requestFocus()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()){
            email.error = "Email unvalid"
            email.requestFocus()
            return false
        }

        if(phone.text.length != 8){
            phone.error = "Phone number must be 8 digits"
            phone.requestFocus()
            return false
        }

        if(!phone.text.toString().trim().matches(Regex("[0-9]+"))){
            phone.error = "Phone number must be digits"
            phone.requestFocus()
            return false
        }

        return true
    }

    private fun register(username: String, password: String, email: String, phone: String) {
        loadingDialog.startLoadingDialog()

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val id : String = ""
        val registerInfo = User(id, username, email, password, phone)

        retIn.registerUser(registerInfo).enqueue(object :
            Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadingDialog.dismissDialog()
                Toast.makeText(
                    this@RegisterActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>

            ) {
                loadingDialog.dismissDialog()
                if (response.code() == 200) {
                    val msg =Gson().fromJson(response.body()?.string(), JsonObject::class.java).get("message").asString
                    Toast.makeText(this@RegisterActivity,msg,  Toast.LENGTH_SHORT).show()
                        val gson = Gson()
                        val jsonSTRING = response.body()?.string()
                        val jsonObject = gson.fromJson(jsonSTRING, JsonObject::class.java)
                        val user = jsonObject.get("user").asJsonObject
                        val id_user = user.get("_id").asString
                        val username_user = user.get("username").asString
                        val email_user = user.get("email").asString
                        val phone_user = user.get("phone").asString
                        sessionPref.createRegisterSession(id_user, username_user, email_user,"", phone_user)
                        val intent = Intent(this@RegisterActivity, MainMenuActivity::class.java)
                        startActivity(intent)
                        finish()


                }
                else{
                    Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}