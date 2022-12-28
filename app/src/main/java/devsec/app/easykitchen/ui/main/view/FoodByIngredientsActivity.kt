package devsec.app.easykitchen.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.utils.services.Cart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodByIngredientsActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var foodArrayList: ArrayList<Food>
    private lateinit var emptyRecipeLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ingredient_filter_food)
        emptyRecipeLayout = findViewById(R.id.noFoodByIngredientsLayout)
        emptyRecipeLayout.visibility = LinearLayout.GONE
        toolbar = findViewById(R.id.foodSearchBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener() {
            onBackPressed()
        }

        getFoodByIngredients()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.foodByIngredientsListView)
        recyclerView.layoutManager = layoutManager
        adapter = FoodAdapter(foodArrayList)
        Log.d("FoodByIngredients", foodArrayList.toString())
        recyclerView.adapter = adapter

        if (foodArrayList.size == 0) {
//            emptyRecipeLayout.visibility = LinearLayout.VISIBLE
        }

        adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@FoodByIngredientsActivity , FoodRecipeActivity::class.java)
                intent.putExtra("id", foodArrayList[position].id)
                startActivity(intent)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.food_menu, menu)
        val search = menu?.findItem(R.id.food_search)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)

    }

    private fun getFoodByIngredients(){
        foodArrayList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsList()
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    val foodList = response.body()
                    for (food in foodList!!) {
                        if (foodFilter(food) == true) {
                            Log.d("FoodFilter", foodFilter(food).toString())
                            foodArrayList.add(food)
                        }
                    }
                    if (foodArrayList.size == 0) {
                        emptyRecipeLayout.visibility = LinearLayout.VISIBLE
                    }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    private fun foodFilter(food: Food): Boolean {
        val ingredients = ArrayList<String>()
        if(!food.strIngredient1.isNullOrEmpty() && food.strIngredient1.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient1.toString()) }
        if(!food.strIngredient2.isNullOrEmpty() && food.strIngredient2.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient2.toString()) }
        if(!food.strIngredient3.isNullOrEmpty() && food.strIngredient3.toString().trim().isNotBlank()){ ingredients.add(food?.strIngredient3.toString()) }
        if(!food.strIngredient4.isNullOrEmpty() && food.strIngredient4.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient4.toString()) }
        if(!food.strIngredient5.isNullOrEmpty() && food.strIngredient5.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient5.toString()) }
        if(!food.strIngredient6.isNullOrEmpty() && food.strIngredient6.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient6.toString()) }
        if(!food.strIngredient7.isNullOrEmpty() && food.strIngredient7.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient7.toString()) }
        if(!food.strIngredient8.isNullOrEmpty() && food?.strIngredient8.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient8.toString()) }
        if(!food.strIngredient9.isNullOrEmpty() && food.strIngredient9.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient9.toString()) }
        if(!food.strIngredient10.isNullOrEmpty() && food.strIngredient10.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient10.toString()) }
        if(!food.strIngredient11.isNullOrEmpty() && food.strIngredient11.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient11.toString()) }
        if(!food.strIngredient12.isNullOrEmpty() && food.strIngredient12.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient12.toString()) }
        if(!food.strIngredient13.isNullOrEmpty() && food.strIngredient13.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient13.toString()) }
        if(!food.strIngredient14.isNullOrEmpty() && food.strIngredient14.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient14.toString()) }
        if(!food.strIngredient15.isNullOrEmpty() && food.strIngredient15.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient15.toString()) }
        if(!food.strIngredient16.isNullOrEmpty() && food.strIngredient16.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient16.toString()) }
        if(!food.strIngredient17.isNullOrEmpty() && food.strIngredient17.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient17.toString()) }
        if(!food.strIngredient18.isNullOrEmpty() && food.strIngredient18.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient18.toString()) }
        if(!food.strIngredient19.isNullOrEmpty() && food.strIngredient19.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient19.toString()) }
        if(!food.strIngredient20.isNullOrEmpty() && food.strIngredient20.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient20.toString()) }
        if (ingredients.containsAll(Cart.cart)) {
            Log.d("Food", Cart.cart.toString())
            return true
        }
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchArray = ArrayList<Food>()
        for (food in foodArrayList) {
            if (food.name.toLowerCase().contains(newText.toString().toLowerCase())) {
                searchArray.add(food)
            }
        }
        adapter = FoodAdapter( searchArray)
        adapter.notifyDataSetChanged()
        return false
    }
}