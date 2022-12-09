package devsec.app.easykitchen.ui.main.fragments

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
import devsec.app.easykitchen.data.models.Category
import devsec.app.easykitchen.ui.main.adapter.CategoryAdapter
import devsec.app.easykitchen.ui.main.adapter.ExpertAdapter
import devsec.app.easykitchen.ui.main.adapter.RecommendedFoodAdapter
import devsec.app.easykitchen.data.models.RecettesInQueue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecommendedFoodAdapter
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: CategoryAdapter
    private lateinit var recyclerView3: RecyclerView
    private lateinit var adapter3: ExpertAdapter
    private lateinit var title: TextView
    private lateinit var recetteList: ArrayList<RecettesInQueue.Recette>
    private lateinit var categoryList: ArrayList<Category>



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
        recyclerView = view.findViewById(R.id.recommendedView)
        recyclerView.setHasFixedSize(true)
//        initRecette()


        initCategorie()
        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2 = view.findViewById(R.id.categorieView)
        recyclerView2.layoutManager = layoutManager2
        recyclerView2.setHasFixedSize(true)
        adapter2 = CategoryAdapter(categoryList)
        recyclerView2.adapter = adapter2

        recyclerView3 = view.findViewById(R.id.expertView)
        adapter3 = ExpertAdapter()

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

                adapter = RecommendedFoodAdapter(response.body()!!.recettes)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)


            }

            override fun onFailure(call: Call<RecettesInQueue>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })

    }

    private fun initCategorie() {
        categoryList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getCategoriesList()
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    for (category in categories!!) {
                        categoryList.add(category)
                        }
                    }
                    adapter2.notifyDataSetChanged()
                }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })
    }
}