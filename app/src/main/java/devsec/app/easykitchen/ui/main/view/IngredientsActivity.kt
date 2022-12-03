package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.adapter.IngredientsAdapter

class IngredientsActivity : AppCompatActivity() {
    lateinit var adapter: IngredientsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var ingredientsArrayList: ArrayList<String>

    lateinit var ingredientsName : Array<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)

        initIngredientsList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.ingredientsRV)
        recyclerView.layoutManager = layoutManager
        adapter = IngredientsAdapter(ingredientsArrayList)
        recyclerView.adapter = adapter



        val toolbar = findViewById<Toolbar>(R.id.ingredientsToolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ingredients_menu, menu)
        val searchView = menu?.findItem(R.id.ingredients_search)?.actionView as SearchView
        searchView.queryHint = "Search for ingredients"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {


                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun initIngredientsList(){
        ingredientsName = arrayOf("Apple", "Banana", "Orange", "Mango", "Pineapple", "Strawberry", "Watermelon", "Grapes", "Peach", "Pear", "Cherry", "Blueberry", "Raspberry", "Lemon", "Lime", "Coconut", "Papaya", "Kiwi", "Tomato", "Potato", "Onion", "Garlic", "Carrot", "Cucumber", "Spinach", "Broccoli", "Cabbage", "Mushroom", "Pepper", "Eggplant", "Corn", "Beetroot", "Cauliflower", "Zucchini", "Sweet Potato", "Celery", "Asparagus", "Avocado", "Pumpkin", "Chili", "Mango", "Pineapple", "Strawberry", "Watermelon", "Grapes", "Peach", "Pear", "Cherry", "Blueberry", "Raspberry", "Lemon", "Lime", "Coconut", "Papaya", "Kiwi", "Tomato", "Potato", "Onion", "Garlic", "Carrot", "Cucumber", "Spinach", "Broccoli", "Cabbage", "Mushroom", "Pepper", "Eggplant", "Corn", "Beetroot", "Cauliflower", "Zucchini", "Sweet Potato", "Celery", "Asparagus", "Avocado", "Pumpkin", "Chili", "Mango", "Pineapple", "Strawberry", "Watermelon", "Grapes", "Peach", "Pear", "Cherry", "Blueberry", "Raspberry", "Lemon", "Lime", "Coconut", "Papaya", "Kiwi", "Tomato", "Potato", "Onion", "Garlic", "Carrot", "Cucumber", "Spinach", "Broccoli", "Cabbage", "Mushroom", "Pepper", "Eggplant", "Corn", "Beetroot", "Cauliflower", "Zucchini", "Sweet Potato", "Celery", "Asparagus", "Avocado", "Pumpkin", "Chili", "Mango", "Pineapple", "Strawberry", "Watermelon")
        ingredientsArrayList = ArrayList()
        ingredientsArrayList.addAll(ingredientsName)
    }
}