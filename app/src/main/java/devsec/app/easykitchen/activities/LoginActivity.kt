package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
            if (true) {
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
            }else{
                val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG)
                toast.show()
            }

        }



    }
}