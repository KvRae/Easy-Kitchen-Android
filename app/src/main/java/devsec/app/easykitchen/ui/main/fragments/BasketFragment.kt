package devsec.app.easykitchen.ui.main.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils.attachBadgeDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.data.models.Ingredients
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.ui.main.adapter.IngredientsAdapter
import devsec.app.easykitchen.ui.main.view.FoodByIngredientsActivity
import devsec.app.easykitchen.ui.main.view.FoodRecipeActivity
import devsec.app.easykitchen.ui.main.view.IngredientsCartActivity
import devsec.app.easykitchen.utils.services.Cart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BasketFragment : Fragment() {
    private lateinit var adapter: IngredientsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientsArrayList: ArrayList<String>

    private lateinit var badge : BadgeDrawable
    lateinit var toolbar: Toolbar
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIngredientsList()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.ingredientsBasketRV)
        recyclerView.layoutManager = layoutManager
        adapter = IngredientsAdapter(ingredientsArrayList)
        recyclerView.adapter = adapter

        badge = BadgeDrawable.create(requireContext())
        badge.isVisible = true
        badge.number = Cart.cart.size

        adapter.setOnItemClickListener(object : IngredientsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = ingredientsArrayList[position]
                Cart.cart.add(item)
                ingredientsArrayList.remove(item)
                badge.number = Cart.cart.size
                Log.d("Cart", Cart.cart.toString())

                adapter.notifyDataSetChanged()
            }
        })

        toolbar = view.findViewById(R.id.ingredientsBasketToolbar)
        val searchFoodButton = view.findViewById<FloatingActionButton>(R.id.searchFoodFloatingActionButton)
        searchFoodButton.setOnClickListener {
            if (Cart.cart.size != 0) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Are you sure?")
                    .setMessage("You want to go for recipes with those ingredients?")
                    .setPositiveButton("Yes") { dialog, which ->
                        val intent = Intent(context, FoodByIngredientsActivity::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()

            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("No ingredients selected")
                    .setMessage("You didn't add ingredients to your basket yet")
                    .setPositiveButton("Ok") { dialog, which ->
                    }
                    .show()
            }
            true
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ingredients_cart -> {
                    val intent = Intent(context, IngredientsCartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        searchView = toolbar.menu.findItem(R.id.ingredients_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        })

        attachBadgeDrawable(badge, toolbar, R.id.ingredients_cart)

    }

    private fun filterList(newText: String) {
        val filteredList = ArrayList<String>()
        for (item in ingredientsArrayList) {
            if (item.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()){
            android.app.AlertDialog.Builder(requireContext())
                .setTitle("No Result")
                .setMessage("No food found with the keyword $newText")
                .setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        } else {
            adapter.filterList(filteredList)
            adapter.setOnItemClickListener(object : IngredientsAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val item = filteredList[position]
                    Cart.cart.add(item)
                    filteredList.remove(item)
                    ingredientsArrayList.remove(item)
                    badge.number = Cart.cart.size
                    Log.d("Cart", Cart.cart.toString())
                    adapter.notifyDataSetChanged()
                }
            })


        }

    }


    private fun initIngredientsList() {
        ingredientsArrayList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getIngredientsList()
        call.enqueue(object : Callback<List<Ingredients>> {
            override fun onResponse(call: Call<List<Ingredients>>, response: Response<List<Ingredients>>) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()
                    if (ingredientsList != null) {
                        for (ingredient in ingredientsList) {
                            ingredientsArrayList.add(ingredient.name)
                        }
                        Log.d("TAG", "onResponse: $ingredientsArrayList")
                    }
                    ingredientsArrayList.removeAll(Cart.cart)
                    ingredientsArrayList.addAll(Cart.cartRemovedItems)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Ingredients>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}