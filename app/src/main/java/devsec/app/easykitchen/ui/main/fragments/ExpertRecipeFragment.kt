package devsec.app.easykitchen.ui.main.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.data.models.Recette
import devsec.app.easykitchen.data.models.User
import devsec.app.easykitchen.ui.main.adapter.BlogAdapter
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.ui.main.adapter.RecetteTypeAdapter
import devsec.app.easykitchen.ui.main.view.FoodRecipeActivity
import devsec.app.easykitchen.ui.main.view.RecetteActivity
import devsec.app.easykitchen.ui.main.view.RecetteFormActivity
import devsec.app.easykitchen.utils.services.Cart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExpertRecipeFragment : Fragment(), SearchView.OnQueryTextListener {


    lateinit var formButton: FloatingActionButton

    private lateinit var recetteArray: ArrayList<Recette>
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var foodArrayList: java.util.ArrayList<Food>
    private lateinit var emptyRecipeLayout: LinearLayout
    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expert_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyRecipeLayout = view.findViewById(R.id.noIngredientFilterFoodLayout)
        emptyRecipeLayout.visibility = LinearLayout.GONE
        toolbar = view.findViewById(R.id.foodIngredientSearchBar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.setTitle("")
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener() {
            activity?.finish()
        }

        getFoodByIngredients()
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.foodList)
        recyclerView.layoutManager = layoutManager
        adapter = FoodAdapter(foodArrayList)
        Log.d("FoodByIngredients", foodArrayList.toString())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, FoodRecipeActivity::class.java)
                intent.putExtra("id", foodArrayList[position].id)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_menu, menu)
        val search = menu.findItem(R.id.food_search)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getFoodByIngredients(){
        foodArrayList = java.util.ArrayList()
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
        val ingredients = java.util.ArrayList<String>()
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


    private fun filterList(newText: String) {
        val filteredList = java.util.ArrayList<Food>()
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
            AlertDialog.Builder(context)
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
                    val intent = Intent(context, FoodRecipeActivity::class.java)
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
