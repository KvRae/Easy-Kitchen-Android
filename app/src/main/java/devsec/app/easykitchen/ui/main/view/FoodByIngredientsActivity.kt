package devsec.app.easykitchen.ui.main.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.data.models.Recette
import devsec.app.easykitchen.ui.main.adapter.BlogAdapter
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.ui.main.adapter.RecetteTypeAdapter
import devsec.app.easykitchen.utils.services.Cart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FoodByIngredientsActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var foodArrayList: ArrayList<Food>
    private lateinit var emptyRecipeLayout: LinearLayout
    private lateinit var searchView: SearchView

    // RecetteType declaration
    private lateinit var recetteTypeList:ArrayList<String>
    private lateinit var recetteTypeRecyclerView: RecyclerView
    private lateinit var recetteTypeAdapter: RecetteTypeAdapter
     private lateinit var expertButton: MaterialButton
     private lateinit var userButton: MaterialButton
     private lateinit var expertLL: LinearLayout
     private lateinit var recetteLL: LinearLayout

    private lateinit var recetteArrayList:ArrayList<Recette>
    private lateinit var recetteList:ArrayList<Recette>
    private lateinit var blogRecyclerView: RecyclerView
    private lateinit var blogAdapter: BlogAdapter






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_filter_food)

        emptyRecipeLayout = findViewById(R.id.noFoodByIngredientsLayout)
        emptyRecipeLayout.visibility = LinearLayout.GONE
        toolbar = findViewById(R.id.foodIngredientSearchBar)
        setSupportActionBar(toolbar)
        toolbar.setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener() {
            onBackPressed()
        }






         // Food init and implementation

        expertButton=findViewById(R.id.expertButton)

        userButton=findViewById(R.id.recetteUserButton)
        expertLL=findViewById(R.id.foodLL)
        recetteLL=findViewById(R.id.recetteLL)




        getRecetteByIngredients()
        Log.d("recetteArrayList",recetteArrayList.toString())

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

            // Blog init and implementation


            val recettelayoutManager = LinearLayoutManager(this)

            blogRecyclerView = findViewById(R.id.recetteByIngredientsListView)

            blogRecyclerView.layoutManager = recettelayoutManager

            blogAdapter = BlogAdapter(recetteArrayList)

            blogRecyclerView.adapter = blogAdapter

            blogAdapter.setOnItemClickListener(object : BlogAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@FoodByIngredientsActivity, RecetteActivity::class.java)
                    intent.putExtra("id", recetteArrayList[position].id)
                    startActivity(intent)
                }
            })

        recetteLL.visibility=View.GONE
        expertButton.setBackgroundColor(getResources().getColor(R.color.rosybrown))
        expertButton.setTextColor(getResources().getColor(R.color.theWhite))

        expertButton.setOnClickListener {

                expertLL.visibility= View.VISIBLE
                recetteLL.visibility=View.GONE
                expertButton.setBackgroundColor(getResources().getColor(R.color.rosybrown))
                expertButton.setTextColor(getResources().getColor(R.color.theWhite))
                userButton.setBackgroundColor(getResources().getColor(R.color.theWhite))
                userButton.setTextColor(getResources().getColor(R.color.rosybrown))


        }
        userButton.setOnClickListener {

                recetteLL.visibility= View.VISIBLE
                expertLL.visibility=View.GONE
                userButton.setBackgroundColor(getResources().getColor(R.color.rosybrown))
                userButton.setTextColor(getResources().getColor(R.color.theWhite))
                expertButton.setBackgroundColor(getResources().getColor(R.color.theWhite))
                expertButton.setTextColor(getResources().getColor(R.color.rosybrown))


        }

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

    private fun getRecetteByIngredients(){
        recetteArrayList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getRecette()
        call.enqueue(object : Callback<List<Recette>> {
            override fun onResponse(call: Call<List<Recette>>, response: Response<List<Recette>>) {
                if (response.isSuccessful) {
                    val recetteList = response.body()
                    for (recette in recetteList!!) {
                        if (recetteFilter(recette)) {
                            recetteArrayList.add(recette)

                            Log.d("recetteArrayList",recetteArrayList.toString())

                        }
                    }
                    if (recetteArrayList.size == 0) {
                        emptyRecipeLayout.visibility = LinearLayout.VISIBLE
                    }
                    blogAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<List<Recette>>, t: Throwable) {
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

    private fun recetteFilter(recette: Recette): Boolean {
        val ingredients = ArrayList<String>()
        if(!recette.strIngredient1.isNullOrEmpty() && recette.strIngredient1.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient1.toString()) }
        if(!recette.strIngredient2.isNullOrEmpty() && recette.strIngredient2.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient2.toString()) }
        if(!recette.strIngredient3.isNullOrEmpty() && recette.strIngredient3.toString().trim().isNotBlank()){ ingredients.add(recette?.strIngredient3.toString()) }
        if(!recette.strIngredient4.isNullOrEmpty() && recette.strIngredient4.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient4.toString()) }
        if(!recette.strIngredient5.isNullOrEmpty() && recette.strIngredient5.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient5.toString()) }
        if(!recette.strIngredient6.isNullOrEmpty() && recette.strIngredient6.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient6.toString()) }
        if(!recette.strIngredient7.isNullOrEmpty() && recette.strIngredient7.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient7.toString()) }
        if(!recette.strIngredient8.isNullOrEmpty() && recette.strIngredient8.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient8.toString()) }
        if(!recette.strIngredient9.isNullOrEmpty() && recette.strIngredient9.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient9.toString()) }
        if(!recette.strIngredient10.isNullOrEmpty() && recette.strIngredient10.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient10.toString()) }
        if(!recette.strIngredient11.isNullOrEmpty() && recette.strIngredient11.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient11.toString()) }
        if(!recette.strIngredient12.isNullOrEmpty() && recette.strIngredient12.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient12.toString()) }
        if(!recette.strIngredient13.isNullOrEmpty() && recette.strIngredient13.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient13.toString()) }
        if(!recette.strIngredient14.isNullOrEmpty() && recette.strIngredient14.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient14.toString()) }
        if(!recette.strIngredient15.isNullOrEmpty() && recette.strIngredient15.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient15.toString()) }
        if(!recette.strIngredient16.isNullOrEmpty() && recette.strIngredient16.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient16.toString()) }
        if(!recette.strIngredient17.isNullOrEmpty() && recette.strIngredient17.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient17.toString()) }
        if(!recette.strIngredient18.isNullOrEmpty() && recette.strIngredient18.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient18.toString()) }
        if(!recette.strIngredient19.isNullOrEmpty() && recette.strIngredient19.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient19.toString()) }
        if(!recette.strIngredient20.isNullOrEmpty() && recette.strIngredient20.toString().trim().isNotBlank()) { ingredients.add(recette?.strIngredient20.toString()) }

        if (ingredients.containsAll(Cart.cart)) {
            Log.d("recette", Cart.cart.toString())
            return true
        }
        return false
    }

    private fun filterList(newText: String) {
        val filteredList = ArrayList<Food>()
        for (item in foodArrayList) {
            if (item.name.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
            else if (item.area.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
            else if (item.category.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("No Result")
                .setMessage("No food found with the keyword $newText")
                .setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        } else {
            adapter.filterList(filteredList)
            adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@FoodByIngredientsActivity, FoodRecipeActivity::class.java)
                    intent.putExtra("id", filteredList[position].id)
                    startActivity(intent)
                }
            })


        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            filterList(newText)
        }
        return true
    }
}