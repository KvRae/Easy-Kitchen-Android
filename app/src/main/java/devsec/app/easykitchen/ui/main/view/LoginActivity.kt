package devsec.app.easykitchen.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.User
import devsec.app.easykitchen.utils.services.LoadingDialog
import devsec.app.easykitchen.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var restApiService: RestApiService
    lateinit var sessionPref: SessionPref

    lateinit var usernameLayout: TextInputLayout
    lateinit var passwordLayout: TextInputLayout
    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadingDialog = LoadingDialog(this)
        //our login session manager
        sessionPref = SessionPref(this)
        if (sessionPref.isLoggedIn()) {
            val intent = Intent(this, MainMenuActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        usernameLayout = findViewById(R.id.LoginInputLayout)
        passwordLayout = findViewById(R.id.PasswordInputLayout)
        usernameEditText = findViewById(R.id.loginEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val registerBtn = findViewById<TextView>(R.id.RegisterTV)


        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        loginBtn.setOnClickListener() {
            if (validateLogin(usernameEditText, passwordEditText,passwordLayout)) {
                login(usernameEditText.text.trim().toString(), passwordEditText.text.trim().toString())

            }else{
                val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                toast.show()
            }

        }



    }





    private fun validateLogin(username: EditText, password: EditText,passwordlayout : TextInputLayout): Boolean {
        if(username.text.trim().isEmpty() || password.text.trim().isEmpty()){

            if (password.text.isEmpty()) {
                password.error = "Password is required"
                password.requestFocus()

            }
            // made it revesed so it desplays correctly you ll see it in the app
            if (username.text.isEmpty()) {
                username.error = "Username is required"
                username.requestFocus()

            }

            return false

        }

        return true
    }

    private fun login(username: String, password: String) {
        loadingDialog.startLoadingDialog()
            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            val signInInfo = User(username, password)

            retIn.loginUser(signInInfo).enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()


                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        loadingDialog.dismissDialog()
                        // creating a session
                        val gson = Gson()
                        val jsonSTRING = response.body()?.string()
                        val jsonObject = gson.fromJson(jsonSTRING, JsonObject::class.java)
                        val user = jsonObject.get("user").asJsonObject
                        val id_user = user.get("_id").asString
                        val username_user = user.get("username").asString
                        val email_user = user.get("email").asString
                        val phone_user = user.get("phone").asString
                        sessionPref.createRegisterSession(id_user, username_user, email_user, "",phone_user)
                        Toast.makeText(this@LoginActivity, "Welcome!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainMenuActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else if(response.code() == 401){
                        loadingDialog.dismissDialog()
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        loadingDialog.dismissDialog()
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }