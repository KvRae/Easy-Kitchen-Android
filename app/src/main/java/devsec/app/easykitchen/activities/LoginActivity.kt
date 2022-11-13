package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {
    lateinit var restApiService: RestApiService
    internal var compositeDisposable = CompositeDisposable()

    lateinit var usernameLayout: TextInputLayout
    lateinit var passwordLayout: TextInputLayout
    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText


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

        return true
    }

    private fun login(username: String, password: String) {
            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            val signInInfo = User(username, password)
            retIn.loginUser(signInInfo).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        Toast.makeText(this@LoginActivity, "Welcome!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainMenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }


    }