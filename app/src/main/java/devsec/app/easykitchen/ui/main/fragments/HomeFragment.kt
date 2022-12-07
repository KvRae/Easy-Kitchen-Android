package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.ui.main.view.RecetteActivity
import devsec.app.easykitchen.ui.main.adapter.Categorie_RecyclerView
import devsec.app.easykitchen.ui.main.adapter.Expert_RecyclerView
import devsec.app.easykitchen.ui.main.adapter.Recommended_RecyclerView
import devsec.app.easykitchen.data.models.RecettesInQueue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Recommended_RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: Categorie_RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var adapter3: Expert_RecyclerView
    private lateinit var title: TextView
    private lateinit var recetteList: ArrayList<RecettesInQueue.Recette>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        title = view.findViewById(R.id.title)


//        recyclerView = view.findViewById(R.id.recommendedView)
//        recyclerView.setHasFixedSize(true)
//        initRecette()

        recyclerView2 = view.findViewById(R.id.categorieView)
        adapter2 = Categorie_RecyclerView()

        recyclerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerView2.adapter = adapter2

        recyclerView3 = view.findViewById(R.id.expertView)
        adapter3 = Expert_RecyclerView()

        recyclerView3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerView3.adapter = adapter3

        val next = view.findViewById<Button>(R.id.menu)
//
//        next.setOnClickListener() {
//            val intent = Intent(context, RecetteActivity::class.java)
//            startActivity(intent)
//        }



    }
    private fun initRecette() {

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)

        retIn.getRecette().enqueue(object : Callback<RecettesInQueue> {
            override fun onResponse(
                call: Call<RecettesInQueue>,
                response: Response<RecettesInQueue>
            ) {

                adapter = Recommended_RecyclerView(response.body()!!.recettes)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)


            }

            override fun onFailure(call: Call<RecettesInQueue>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })

    }
}