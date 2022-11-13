package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerbtn = findViewById<Button>(R.id.RegisterBTN)

        registerbtn.setOnClickListener {

            val username = findViewById<EditText>(R.id.loginEditText)
            val password = findViewById<EditText>(R.id.passwordInputEditText)
            val verifPass = findViewById<EditText>(R.id.passwordInputEditText2)
            val email = findViewById<EditText>(R.id.emailEditText)
            val phone = findViewById<EditText>(R.id.phoneEditText)

            if (validateRegister(username, password, verifPass, email, phone)) {
                register(username.text.toString(), password.text.toString(), email.text.toString(), phone.text.toString())}
        }
        val loginbtn = findViewById<Button>(R.id.loginBtn)
        loginbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateRegister(username: EditText, password: EditText, verifPass: EditText, email: EditText, phone: EditText): Boolean {
        if (username.text.isEmpty() || password.text.isEmpty() || verifPass.text.isEmpty() || email.text.isEmpty() || phone.text.isEmpty()) {

            if (phone.text.isEmpty()) {
                phone.error = "Phone is required"
                phone.requestFocus()
                return false
            }

            if (email.text.isEmpty()) {
                email.error = "Email is required"
                email.requestFocus()
                return false
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
        if(phone.text.length != 10){
            phone.error = "Phone number must be 10 digits"
            phone.requestFocus()
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

        if(phone.text.toString().matches(Regex("[0-9]+"))){
            phone.error = "Phone number must be digits"
            phone.requestFocus()
            return false
        }

        return true
    }

    private fun register(username: String, password: String, email: String, phone: String) {
            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            val id : String = ""
            val registerInfo = User(id, username, password, email, phone)

        retIn.registerUser(registerInfo).enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
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
                if (response.code() == 201) {
                    Toast.makeText(this@RegisterActivity, "Registration success!", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)

                }
                else{
                    Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}