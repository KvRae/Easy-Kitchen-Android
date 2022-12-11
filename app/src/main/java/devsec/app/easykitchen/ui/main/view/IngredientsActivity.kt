package devsec.app.easykitchen.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.view.Menu
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.BadgeUtils.*
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Ingredients
import devsec.app.easykitchen.databinding.ActivityIngredientsBinding
import devsec.app.easykitchen.ui.main.adapter.IngredientsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IngredientsActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityIngredientsBinding
    private lateinit var badge : BadgeDrawable
     var counter : Int = 0
    lateinit var toolbar: Toolbar

    private lateinit var adapter: IngredientsAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var ingredientsArrayList: ArrayList<String>
    private lateinit var searchArrayList: ArrayList<String>



    var igredientCart: List<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)

        initIngredientsList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.ingredientsRV)
        recyclerView.layoutManager = layoutManager
        searchArrayList = ingredientsArrayList
        adapter = IngredientsAdapter(searchArrayList)
        recyclerView.adapter = adapter


        adapter.setOnItemClickListener(object : IngredientsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = searchArrayList[position]
                val itemView = recyclerView.findViewHolderForAdapterPosition(position)

                if (igredientCart.contains(item)) {
                    igredientCart = igredientCart.minus(item)

                    itemView!!.itemView.findViewById<ImageButton>(R.id.ingredientItemIcon)
                        .setImageResource(R.drawable.ic_baseline_add_24)
                    itemView!!.itemView.findViewById<TextView>(R.id.ingredient_name)
                        .setTextColor(resources.getColor(androidx.constraintlayout.widget.R.color.material_grey_600))

                } else {
                    igredientCart = igredientCart.plus(item)

                    itemView!!.itemView.findViewById<ImageButton>(R.id.ingredientItemIcon)
                        .setImageResource(R.drawable.ic_baseline_remove_24)
                    itemView!!.itemView.findViewById<TextView>(R.id.ingredient_name)
                        .setTextColor(resources.getColor(androidx.appcompat.R.color.material_grey_300))

                }
                badge.number = igredientCart.size



            }
        })


         toolbar = findViewById<Toolbar>(R.id.ingredientsToolbar)
        setSupportActionBar(toolbar)


        //********************Badge*************************//

        val cartItem = toolbar.menu.findItem(R.id.ingredients_cart).itemId
        badge = BadgeDrawable.create(this)
        badge.isVisible = true
        badge.number = igredientCart.size
        toolbar.setNavigationOnClickListener {
            finish()
        }



    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.ingredients_menu, menu)
        attachBadgeDrawable(badge ,toolbar, R.id.ingredients_cart)

        val searchItem = menu?.findItem(R.id.ingredients_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search for ingredients"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                searchArrayList.clear()
                if (searchText.isNotEmpty()) {
                    ingredientsArrayList.forEach {
                        if (it.lowercase(Locale.getDefault()).contains(searchText)) {
                            searchArrayList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    searchArrayList.clear()
                    searchArrayList.addAll(ingredientsArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                this@IngredientsActivity.searchArrayList =ingredientsArrayList
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun getIngredients(){
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getIngredientsList()
        call.enqueue(object : Callback<List<Ingredients>> {
            override fun onResponse(
                call: Call<List<Ingredients>>,
                response: Response<List<Ingredients>>
            ) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()
                    for (i in ingredientsList!!) {
                        ingredientsArrayList.add(i.name)
                        }
                    }
                adapter.notifyDataSetChanged()
                }

            override fun onFailure(call: Call<List<Ingredients>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun initIngredientsList(){
        ingredientsArrayList = ArrayList()
        searchArrayList = ArrayList()
        getIngredients()

    }
}