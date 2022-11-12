package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {
    lateinit var restApiService: RestApiService
    internal var compositeDisposable = CompositeDisposable()

    lateinit var usernameLayout: TextInputLayout
    lateinit var passwordLayout: TextInputLayout
    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText


    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val registerBtn = findViewById<TextView>(R.id.RegisterTV)


        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        loginBtn.setOnClickListener() {

             usernameLayout = findViewById(R.id.LoginInputLayout)
             passwordLayout = findViewById(R.id.PasswordInputLayout)
             usernameEditText = findViewById(R.id.loginEditText)
             passwordEditText = findViewById(R.id.passwordEditText)

            if (validateLogin(usernameEditText, passwordEditText,passwordLayout)) {
                login(usernameEditText.text.toString(), passwordEditText.text.toString())
            }else{
                val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                toast.show()
            }

        }



    }





    private fun validateLogin(username: EditText, password: EditText,passwordlayout : TextInputLayout): Boolean {
        if(username.text.isEmpty() || password.text.isEmpty()){

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
        if (username.text.toString() != "admin" && password.text.toString() != "admin") {
            return false
        }

        return true
    }

    private fun login(username: String, password: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.15:3000/api/login"

        val requestBody =
            "username=" + URLEncoder.encode(
                username,
                "UTF-8"
            ) + "&password=" + password
        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    if (response == "true") {
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                },
                Response.ErrorListener { error ->
                    val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                    toast.show()
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        queue.add(stringReq)
    }

}