package devsec.app.easykitchen.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import devsec.app.easykitchen.R

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()

        val activity: Activity = GuideActivity()

        handler.postDelayed({
            val intent = Intent(this, activity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}