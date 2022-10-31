package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import devsec.app.easykitchen.R

class LoginActivity : AppCompatActivity() {
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

            val usernameLayout = findViewById<TextInputLayout>(R.id.LoginInputLayout)
            val passwordLayout = findViewById<TextInputLayout>(R.id.PasswordInputLayout)
            val usernameEditText = findViewById<TextView>(R.id.loginEditText)
            val passwordEditText = findViewById<TextView>(R.id.passwordEditText)

            if (validateLogin(usernameEditText, passwordEditText,passwordLayout)) {
                val intent = Intent(this, MainMenuActivity::class.java)
                intent.putExtra("username", usernameEditText.text.toString())
                startActivity(intent)
            }else{
                val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                toast.show()
            }

        }



    }
    private fun validateLogin(username: TextView, password: TextView,passwordlayout : TextInputLayout): Boolean {
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

}