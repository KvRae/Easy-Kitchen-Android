package devsec.app.easykitchen.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.fragments.GuidePageOneFragment


class GuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        val fragment = GuidePageOneFragment()
        val trans = supportFragmentManager.beginTransaction()
        trans.add(R.id.fragmentContainer,fragment)
        trans.commit()

        val skip = findViewById<TextView>(R.id.skipTV)
        skip.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}