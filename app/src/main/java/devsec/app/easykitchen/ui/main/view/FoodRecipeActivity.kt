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
import devsec.app.easykitchen.ui.main.adapter.MeasuresTextAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRecipeActivity : AppCompatActivity() {
    lateinit var id : String
    lateinit var ingredientsList : ArrayList<String>
    lateinit var mesuresList : ArrayList<String>

    lateinit var ingredientsRecyclerView: RecyclerView
    lateinit var mesuresRecyclerView: RecyclerView

    lateinit var ingredientsAdapter: IngredientsTextAdapter
    lateinit var measuresAdapter: MeasuresTextAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_recipe)


        id = intent.getStringExtra("id").toString()
        val recipeImage = findViewById<ImageView>(R.id.recipeFoodImage)
        val recipeCategory = findViewById<TextView>(R.id.foodRecipeCategory)
        val recipeName = findViewById<TextView>(R.id.foodRecipeName)
        val recipeInstructions = findViewById<TextView>(R.id.foodRecipeInstructions)

        ingredientsList = ArrayList()
        mesuresList = ArrayList()

        getRecipe(id, recipeImage, recipeCategory, recipeName, recipeInstructions)

        val ingredientsLayoutManager = LinearLayoutManager(this)
        this.ingredientsRecyclerView = findViewById(R.id.ingredientListView)
        this.ingredientsRecyclerView.layoutManager = ingredientsLayoutManager

        val mesuresLayoutManager = LinearLayoutManager(this)
        this.mesuresRecyclerView = findViewById(R.id.mesureListView)
        this.mesuresRecyclerView.layoutManager = mesuresLayoutManager


        val toolbar = findViewById<Toolbar>(R.id.foodRecipeToolbar)
        toolbar.setNavigationOnClickListener {
            finish()

        }
    }

    fun getRecipe(id: String, recipeImage: ImageView, recipeCategory: TextView, recipeName: TextView, recipeInstructions: TextView) {
        val ingredients = ArrayList<String>()
        val measures = ArrayList<String>()
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

                    if(!food?.strIngredient1.isNullOrEmpty() || food?.strIngredient1.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient1.toString()) }
                    if(!food?.strIngredient2.isNullOrEmpty() || food?.strIngredient2.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient2.toString()) }
                    if(!food?.strIngredient3.isNullOrEmpty() || food?.strIngredient3.toString().trim().isNotBlank()){ ingredients.add(food?.strIngredient3.toString()) }
                    if(!food?.strIngredient4.isNullOrEmpty() || food?.strIngredient4.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient4.toString()) }
                    if(!food?.strIngredient5.isNullOrEmpty() || food?.strIngredient5.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient5.toString()) }
                    if(!food?.strIngredient6.isNullOrEmpty() || food?.strIngredient6.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient6.toString()) }
                    if(!food?.strIngredient7.isNullOrEmpty() || food?.strIngredient7.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient7.toString()) }
                    if(!food?.strIngredient8.isNullOrEmpty() || food?.strIngredient8.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient8.toString()) }
                    if(!food?.strIngredient9.isNullOrEmpty() || food?.strIngredient9.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient9.toString()) }
                    if(!food?.strIngredient10.isNullOrEmpty() || food?.strIngredient10.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient10.toString()) }
                    if(!food?.strIngredient11.isNullOrEmpty() || food?.strIngredient11.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient11.toString()) }
                    if(!food?.strIngredient12.isNullOrEmpty() || food?.strIngredient12.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient12.toString()) }
                    if(!food?.strIngredient13.isNullOrEmpty() || food?.strIngredient13.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient13.toString()) }
                    if(!food?.strIngredient14.isNullOrEmpty() || food?.strIngredient14.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient14.toString()) }
                    if(!food?.strIngredient15.isNullOrEmpty() || food?.strIngredient15.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient15.toString()) }
                    if(!food?.strIngredient16.isNullOrEmpty() || food?.strIngredient16.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient16.toString()) }
                    if(!food?.strIngredient17.isNullOrEmpty() || food?.strIngredient17.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient17.toString()) }
                    if(!food?.strIngredient18.isNullOrEmpty() || food?.strIngredient18.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient18.toString()) }
                    if(!food?.strIngredient19.isNullOrEmpty() || food?.strIngredient19.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient19.toString()) }
                    if(!food?.strIngredient20.isNullOrEmpty() || food?.strIngredient20.toString().trim().isNotBlank()) { ingredients.add(food?.strIngredient20.toString()) }

                    ingredientsList.addAll(ingredients.filter { it.trim().isNotEmpty() })

                    ingredientsAdapter = IngredientsTextAdapter(ingredientsList)
                    ingredientsRecyclerView.adapter = ingredientsAdapter

                    if (!food?.strMeasure1.isNullOrEmpty() || food?.strMeasure1.toString().trim().isNotBlank()) { measures.add(food?.strMeasure1.toString()) }
                    if (!food?.strMeasure2.isNullOrEmpty() || food?.strMeasure2.toString().trim().isNotBlank()) { measures.add(food?.strMeasure2.toString()) }
                    if (!food?.strMeasure3.isNullOrEmpty() || food?.strMeasure3.toString().trim().isNotBlank()) { measures.add(food?.strMeasure3.toString()) }
                    if (!food?.strMeasure4.isNullOrEmpty() || food?.strMeasure4.toString().trim().isNotBlank()) { measures.add(food?.strMeasure4.toString()) }
                    if (!food?.strMeasure5.isNullOrEmpty() || food?.strMeasure5.toString().trim().isNotBlank()) { measures.add(food?.strMeasure5.toString()) }
                    if (!food?.strMeasure6.isNullOrEmpty() || food?.strMeasure6.toString().trim().isNotBlank()) { measures.add(food?.strMeasure6.toString()) }
                    if (!food?.strMeasure7.isNullOrEmpty() || food?.strMeasure7.toString().trim().isNotBlank()) { measures.add(food?.strMeasure7.toString()) }
                    if (!food?.strMeasure8.isNullOrEmpty() || food?.strMeasure8.toString().trim().isNotBlank()) { measures.add(food?.strMeasure8.toString()) }
                    if (!food?.strMeasure9.isNullOrEmpty() || food?.strMeasure9.toString().trim().isNotBlank()) { measures.add(food?.strMeasure9.toString()) }
                    if (!food?.strMeasure10.isNullOrEmpty() || food?.strMeasure10.toString().trim().isNotBlank()) { measures.add(food?.strMeasure10.toString()) }
                    if (!food?.strMeasure11.isNullOrEmpty() || food?.strMeasure11.toString().trim().isNotBlank()) { measures.add(food?.strMeasure11.toString()) }
                    if (!food?.strMeasure12.isNullOrEmpty() || food?.strMeasure12.toString().trim().isNotBlank()) { measures.add(food?.strMeasure12.toString()) }
                    if (!food?.strMeasure13.isNullOrEmpty() || food?.strMeasure13.toString().trim().isNotBlank()) { measures.add(food?.strMeasure13.toString()) }
                    if (!food?.strMeasure14.isNullOrEmpty() || food?.strMeasure14.toString().trim().isNotBlank()) { measures.add(food?.strMeasure14.toString()) }
                    if (!food?.strMeasure15.isNullOrEmpty() || food?.strMeasure15.toString().trim().isNotBlank()) { measures.add(food?.strMeasure15.toString()) }
                    if (!food?.strMeasure16.isNullOrEmpty() || food?.strMeasure16.toString().trim().isNotBlank()) { measures.add(food?.strMeasure16.toString()) }
                    if (!food?.strMeasure17.isNullOrEmpty() || food?.strMeasure17.toString().trim().isNotBlank()) { measures.add(food?.strMeasure17.toString()) }
                    if (!food?.strMeasure18.isNullOrEmpty() || food?.strMeasure18.toString().trim().isNotBlank()) { measures.add(food?.strMeasure18.toString()) }
                    if (!food?.strMeasure19.isNullOrEmpty() || food?.strMeasure19.toString().trim().isNotBlank()) { measures.add(food?.strMeasure19.toString()) }
                    if (!food?.strMeasure20.isNullOrEmpty() || food?.strMeasure20.toString().trim().isNotBlank()) { measures.add(food?.strMeasure20.toString()) }
                    mesuresList.addAll(measures.filter { it.trim().isNotEmpty() })

                    measuresAdapter = MeasuresTextAdapter(mesuresList)
                    mesuresRecyclerView.adapter = measuresAdapter
                    Log.d("mesureList", mesuresList.toString())
                    Log.d("mesures", measures.toString())
                }
            }

            override fun onFailure(call: Call<Food>, t: Throwable) {
                Toast.makeText(this@FoodRecipeActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}