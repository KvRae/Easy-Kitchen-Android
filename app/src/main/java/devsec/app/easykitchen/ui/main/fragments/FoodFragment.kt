package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.ui.main.view.FavoriteFoodActivity
import devsec.app.easykitchen.ui.main.view.IngredientsActivity

class FoodFragment : Fragment() {
    private lateinit var adapter : FoodAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var foodArrayList: ArrayList<Food>

    lateinit var foodImage : IntArray
    lateinit var foodTime : Array<String>
    lateinit var foodName : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFoodList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.foodListView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = FoodAdapter(foodArrayList)
        recyclerView.adapter = adapter

        val toolbar = view.findViewById<Toolbar>(R.id.foodBar)
        toolbar.menu.findItem(R.id.ingredientsCart).setOnMenuItemClickListener {
            val intent = Intent(context, IngredientsActivity::class.java)
            startActivity(intent)
            true
        }
        toolbar.menu.findItem(R.id.favorite_food).setOnMenuItemClickListener {
            val intent = Intent(context, FavoriteFoodActivity::class.java)
            startActivity(intent)
            true
        }
    }

    private fun initFoodList(){
        foodImage = intArrayOf(R.drawable.french_fries_with_cheeseburger, R.drawable.french_fries_with_cheeseburger, R.drawable.french_fries_with_cheeseburger)
        foodTime = arrayOf("10 min", "15 min", "20 min")
        foodName = arrayOf("Food 1", "Food 2", "Food 3")

        foodArrayList = ArrayList()
        for(i in foodImage.indices){
            val food = Food(foodName[i], foodTime[i],foodImage[i] )
            foodArrayList.add(food)
        }



    }



}