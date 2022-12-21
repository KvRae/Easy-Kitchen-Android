package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.ui.main.adapter.IngredientsTextAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRecipeActivity : AppCompatActivity() {
    lateinit var id : String
    lateinit var ingredientsList : ArrayList<String>
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientsTextAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_recipe)


        id = intent.getStringExtra("id").toString()
        val recipeImage = findViewById<ImageView>(R.id.recipeFoodImage)
        val recipeCategory = findViewById<TextView>(R.id.foodRecipeCategory)
        val recipeName = findViewById<TextView>(R.id.foodRecipeName)
        val recipeInstructions = findViewById<TextView>(R.id.foodRecipeInstructions)
        ingredientsList = ArrayList()
        getRecipe(id, recipeImage, recipeCategory, recipeName, recipeInstructions)

        val layoutManager = LinearLayoutManager(this)

        recyclerView = findViewById(R.id.ingredientListView)
        recyclerView.layoutManager = layoutManager



        val toolbar = findViewById<Toolbar>(R.id.foodRecipeToolbar)
        toolbar.setNavigationOnClickListener {
            finish()

        }
    }

    fun getRecipe(id: String, recipeImage: ImageView, recipeCategory: TextView, recipeName: TextView, recipeInstructions: TextView) {
        val ingredients = ArrayList<String>()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodById(id)
        call.enqueue(object : Callback<Food> {
            override fun onResponse(call: Call<Food>, response: Response<Food>) {
                if (response.isSuccessful) {
                    val food = response.body()
                    recipeName.text = food?.name
                    recipeCategory.text = food?.category
                    recipeInstructions.text = food?.instructions
                    Picasso.get().load(food?.image).into(recipeImage)

                    if(!food?.strIngredient1.isNullOrEmpty()) { ingredients.add(food?.strIngredient1.toString()) }
                    if(!food?.strIngredient2.isNullOrEmpty()) { ingredients.add(food?.strIngredient2.toString()) }
                    if(!food?.strIngredient3.isNullOrEmpty()) { ingredients.add(food?.strIngredient3.toString()) }
                    if(!food?.strIngredient4.isNullOrEmpty()) { ingredients.add(food?.strIngredient4.toString()) }
                    if(!food?.strIngredient5.isNullOrEmpty()) { ingredients.add(food?.strIngredient5.toString()) }
                    if(!food?.strIngredient6.isNullOrEmpty()) { ingredients.add(food?.strIngredient6.toString()) }
                    if(!food?.strIngredient7.isNullOrEmpty()) { ingredients.add(food?.strIngredient7.toString()) }
                    if(!food?.strIngredient8.isNullOrEmpty()) { ingredients.add(food?.strIngredient8.toString()) }
                    if(!food?.strIngredient9.isNullOrEmpty()) { ingredients.add(food?.strIngredient9.toString()) }
                    if(!food?.strIngredient10.isNullOrEmpty()) { ingredients.add(food?.strIngredient10.toString()) }
                    if(!food?.strIngredient11.isNullOrEmpty()) { ingredients.add(food?.strIngredient11.toString()) }
                    if(!food?.strIngredient12.isNullOrEmpty()) { ingredients.add(food?.strIngredient12.toString()) }
                    if(!food?.strIngredient13.isNullOrEmpty()) { ingredients.add(food?.strIngredient13.toString()) }
                    if(!food?.strIngredient14.isNullOrEmpty()) { ingredients.add(food?.strIngredient14.toString()) }
                    if(!food?.strIngredient15.isNullOrEmpty()) { ingredients.add(food?.strIngredient15.toString()) }
                    if(!food?.strIngredient16.isNullOrEmpty()) { ingredients.add(food?.strIngredient16.toString()) }
                    if(!food?.strIngredient17.isNullOrEmpty()) { ingredients.add(food?.strIngredient17.toString()) }
                    if(!food?.strIngredient18.isNullOrEmpty()) { ingredients.add(food?.strIngredient18.toString()) }
                    if(!food?.strIngredient19.isNullOrEmpty()) { ingredients.add(food?.strIngredient19.toString()) }
                    if(!food?.strIngredient20.isNullOrEmpty()) { ingredients.add(food?.strIngredient20.toString()) }
                    ingredientsList.addAll(ingredients)
                    adapter = IngredientsTextAdapter(ingredientsList)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Food>, t: Throwable) {
                Toast.makeText(this@FoodRecipeActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}