package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.ui.main.view.FavoriteFoodActivity
import devsec.app.easykitchen.ui.main.view.IngredientsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodFragment : Fragment() {
    private lateinit var adapter : FoodAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var foodArrayList: ArrayList<Food>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFoodList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.foodListView)
        recyclerView.layoutManager = layoutManager
        adapter = FoodAdapter(foodArrayList)
        recyclerView.adapter = adapter

        val toolbar = view.findViewById<Toolbar>(R.id.foodBar)
        toolbar.menu.findItem(R.id.ingredientsCart).setOnMenuItemClickListener {
            val intent = Intent(context, IngredientsActivity::class.java)
            startActivity(intent)
            true
        }
        toolbar.menu.findItem(R.id.favorite_food).setOnMenuItemClickListener {
            val intent = Intent(context, FavoriteFoodActivity::class.java)
            startActivity(intent)
            true
        }
    }

    private fun initFoodList(){
        foodArrayList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsList()
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    foodArrayList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })

    }

}