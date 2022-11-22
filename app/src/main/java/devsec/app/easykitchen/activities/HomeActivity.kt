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
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import android.util.Log

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
        val showGet = findViewById<Button>(R.id.healthy)


        next.setOnClickListener() {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
        }

        showGet.setOnClickListener() {
            showGet()
        }

    }
    private fun showGet() {
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        retIn.getRecette().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@HomeActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                        Log.d("recette" ,response.body().toString())
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(intent)

                }
                else{
                    Toast.makeText(this@HomeActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }
}

