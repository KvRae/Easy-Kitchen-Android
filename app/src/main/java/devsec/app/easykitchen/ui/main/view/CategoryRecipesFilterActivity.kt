package devsec.app.easykitchen.ui.main.view

import android.content.ClipData.newIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRecipesFilterActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FoodAdapter
    lateinit var foodArrayList: ArrayList<Food>
    lateinit var swiperRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_recipes_filter)

        toolbar = findViewById(R.id.foodFilterToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = intent.getStringExtra("category")+" Recipes"
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

//        swiperRefreshLayout = findViewById(R.id.foodSwipeRefresh)
        initFoodList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.foodFilterList)
        recyclerView.layoutManager = layoutManager
        adapter = FoodAdapter(foodArrayList)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val food = foodArrayList[position]
                val intent = Intent(this@CategoryRecipesFilterActivity, FoodRecipeActivity::class.java)
                intent.putExtra("id", food.id)
                startActivity(intent)
            }
        })
    }
    private fun initFoodList() {
        foodArrayList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsList()
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    val foodList = response.body()
                    for (food in foodList!!) {
                        if (food.category == intent.getStringExtra("category")) {
                            foodArrayList.add(food)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("FoodCategoryList", "onFailure: ${t.message}")
            }
        })
    }
}