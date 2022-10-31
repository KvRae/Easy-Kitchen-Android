package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import devsec.app.easykitchen.R

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
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                val toast = Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    }

    private fun validateRegister(username: EditText, password: EditText, verifPass: EditText, email: EditText, phone: EditText): Boolean {
        if (username.text.isEmpty() || password.text.isEmpty() || verifPass.text.isEmpty() || email.text.isEmpty() || phone.text.isEmpty()) {

            if (phone.text.isEmpty()) {
                phone.error = "Phone is required"
                phone.requestFocus()
            }

            if (email.text.isEmpty()) {
                email.error = "Email is required"
                email.requestFocus()
            }


            if (verifPass.text.isEmpty() || password.text.toString() != verifPass.text.toString()) {
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
        return true
    }
}