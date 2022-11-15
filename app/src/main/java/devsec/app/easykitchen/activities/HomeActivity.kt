package devsec.app.easykitchen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.adapter.Categorie_RecyclerView
import devsec.app.easykitchen.adapter.Expert_RecyclerView
import devsec.app.easykitchen.adapter.Recommended_RecyclerView

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Recommended_RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: Categorie_RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var adapter3: Expert_RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recommendedView)
        adapter = Recommended_RecyclerView()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = adapter

        recyclerView2 = findViewById(R.id.categorieView)
        adapter2 = Categorie_RecyclerView()

        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerView2.adapter = adapter2

        recyclerView3 = findViewById(R.id.expertView)
        adapter3 = Expert_RecyclerView()

        recyclerView3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerView3.adapter = adapter3

        val next = findViewById<Button>(R.id.menu)

        next.setOnClickListener() {


                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
        }


    }
}