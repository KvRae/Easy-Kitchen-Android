package devsec.app.easykitchen.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils.*
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Ingredients
import devsec.app.easykitchen.ui.main.adapter.IngredientCartAdapter
import devsec.app.easykitchen.ui.main.adapter.IngredientsAdapter
import devsec.app.easykitchen.ui.main.fragments.BasketFragment
import devsec.app.easykitchen.utils.services.Cart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IngredientsCartActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private lateinit var adapter: IngredientCartAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var ingredientsArrayList: ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)

        initIngredientsList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.ingredientsRV)
        recyclerView.layoutManager = layoutManager
        adapter = IngredientCartAdapter(ingredientsArrayList)
        recyclerView.adapter = adapter



        adapter.setOnItemClickListener(object : IngredientCartAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Cart.cart.minus(ingredientsArrayList[position])
                ingredientsArrayList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, ingredientsArrayList.size)

            }
        })

         toolbar = findViewById(R.id.ingredientsToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            val fragment = BasketFragment()
            val intent = Intent(this, MainMenuActivity::class.java)
            intent.putExtra("fragment", fragment.id)
            startActivity(intent)



        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.ingredients_cart_menu, menu)

        val cartItem = menu!!.findItem(R.id.clear_cart)
        cartItem.setOnMenuItemClickListener { item ->
            if (Cart.cart.size >0){
            AlertDialog.Builder(this)
                .setTitle("Clear Cart")
                .setMessage("Are you sure you want to clear your cart?")
                .setPositiveButton("Yes") { dialog, which ->
                    Cart.cart.clear()
                    adapter.notifyDataSetChanged()
//                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()}
            else{
                AlertDialog.Builder(this)
                    .setTitle("Empty Cart")
                    .setMessage("Your cart is empty")
                    .setPositiveButton("Ok") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
            true

        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initIngredientsList(){
        ingredientsArrayList = ArrayList()
        if (Cart.cart.size > 0) {
            ingredientsArrayList = Cart.cart
        }
    }

}



//
//val item = searchArrayList[position]
//val itemView = recyclerView.findViewHolderForAdapterPosition(position)
//
//if (igredientCart.contains(item)) {
//
//    itemView!!.itemView.findViewById<ImageButton>(R.id.ingredientItemIcon)
//        .setImageResource(R.drawable.ic_baseline_add_24)
//    itemView!!.itemView.findViewById<TextView>(R.id.ingredient_name)
//        .setTextColor(resources.getColor(androidx.constraintlayout.widget.R.color.material_grey_600))
//
//} else {
//
//
//    itemView!!.itemView.findViewById<ImageButton>(R.id.ingredientItemIcon)
//        .setImageResource(R.drawable.ic_baseline_remove_24)
//    itemView!!.itemView.findViewById<TextView>(R.id.ingredient_name)
//        .setTextColor(resources.getColor(androidx.appcompat.R.color.material_grey_300))
//
//}
//Log.d("Cart", Cart.cart.toString())
//

