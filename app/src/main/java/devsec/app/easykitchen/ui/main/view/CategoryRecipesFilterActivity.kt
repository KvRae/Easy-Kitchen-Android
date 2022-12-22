package devsec.app.easykitchen.ui.main.view

import android.content.ClipData.newIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Area
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.ui.main.adapter.AreaAdapter
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
    lateinit var areaList: ArrayList<String>
    lateinit var areaLisView: RecyclerView
    lateinit var areaAdapter: AreaAdapter
    lateinit var area: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_recipes_filter)
        if (intent.hasExtra("area")) {
            area = intent.getStringExtra("area").toString()
        }else{
            area = "All"
        }
        toolbar = findViewById(R.id.foodFilterToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = area +" "+ intent.getStringExtra("category")+" Recipes"
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        initAreaList()
        initFoodList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.foodFilterList)
        recyclerView.layoutManager = layoutManager
        adapter = FoodAdapter(foodArrayList)
        recyclerView.adapter = adapter

        val areaLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        areaLisView = findViewById(R.id.areaFilterList)
        areaLisView.layoutManager = areaLayoutManager
        areaAdapter = AreaAdapter(areaList)
        areaLisView.adapter = areaAdapter

        areaAdapter.setOnItemClickListener(object : AreaAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                area = areaList[position]
                Log.d("Area", area)
                intent.putExtra("area", area)
                finish()
                startActivity(intent)
            }
        })

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
        val area = intent.getStringExtra("area")
        foodArrayList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsList()
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    val foodList = response.body()
                    for (food in foodList!!) {
                        if (food.category == intent.getStringExtra("category")) {
                            if (area != null && area != "All") {
                                if (food.area == area) {
                                    foodArrayList.add(food)
                                }
                            } else {
                                foodArrayList.add(food)
                            }
                        }
                    }
                    if (foodArrayList.size == 0) {
                        Toast.makeText(this@CategoryRecipesFilterActivity, "No Recipes Found", Toast.LENGTH_LONG).show()
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("FoodCategoryList", "onFailure: ${t.message}")
            }
        })
    }


    private fun initAreaList() {
        areaList = ArrayList()
        areaList.add("All")
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getAreasList()
        call.enqueue(object : Callback<List<Area>> {
            override fun onResponse(call: Call<List<Area>>, response: Response<List<Area>>) {
                if (response.isSuccessful) {
                    val areaNameList = response.body()
                    for (area in areaNameList!!) {
                        areaList.add(area.name)
                    }
                    areaAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Area>>, t: Throwable) {
                Log.d("AreaList", "onFailure: ${t.message}")
            }
        })
    }
}