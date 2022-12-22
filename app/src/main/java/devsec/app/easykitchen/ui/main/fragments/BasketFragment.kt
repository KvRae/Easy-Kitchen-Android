package devsec.app.easykitchen.ui.main.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils.attachBadgeDrawable
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Ingredients
import devsec.app.easykitchen.ui.main.adapter.IngredientsAdapter
import devsec.app.easykitchen.ui.main.view.FoodByIngredientsActivity
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
        Cart.cart = ArrayList()
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

                adapter.notifyDataSetChanged()
            }
        })

        toolbar = view.findViewById(R.id.ingredientsBasketToolbar)
        toolbar.setNavigationOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("You want to go for recipes with this cart?")
                .setPositiveButton("Yes") { dialog, which ->
                    val intent = Intent(context, FoodByIngredientsActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()



        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ingredients_search -> {
                    Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.ingredients_cart -> {
                    val intent = Intent(context, IngredientsCartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        attachBadgeDrawable(badge, toolbar, R.id.ingredients_cart)

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
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Ingredients>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}