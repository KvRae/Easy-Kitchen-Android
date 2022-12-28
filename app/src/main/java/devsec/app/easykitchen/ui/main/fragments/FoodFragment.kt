package devsec.app.easykitchen.ui.main.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.ui.main.adapter.FoodAdapter
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.databinding.FragmentFoodBinding
import devsec.app.easykitchen.ui.main.view.FoodRecipeActivity
import devsec.app.easykitchen.utils.services.Cart
import devsec.app.easykitchen.utils.services.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodFragment : Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter : FoodAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var foodArrayList: ArrayList<Food>
    private lateinit var searchView: SearchView

//    val loadingDialog = LoadingDialog(requireActivity())
    private lateinit var swiperRefreshLayout : SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
        searchView = view.findViewById(R.id.foodSearchBar)
        searchView.clearFocus()


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


        swiperRefreshLayout = view.findViewById(R.id.foodIngredientSwipeRefresh)
        initFoodList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.foodListView)
        recyclerView.layoutManager = layoutManager
        adapter = FoodAdapter(foodArrayList)
        recyclerView.adapter = adapter



        swiperRefreshLayout.setOnRefreshListener {
            initFoodList()
            this.swiperRefreshLayout.isRefreshing = false
        }


        adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, FoodRecipeActivity::class.java)
                intent.putExtra("id", foodArrayList[position].id)
                startActivity(intent)
            }
        })


    }

    @SuppressLint("NotifyDataSetChanged")
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
            AlertDialog.Builder(requireContext())
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

    private fun initFoodList(){
        loadingDialog.startLoadingDialog()
        foodArrayList = ArrayList()
        val ingredients = ArrayList<String>()
        foodArrayList.clear()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsList()
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    foodArrayList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    loadingDialog.dismissDialog()
                    Log.d("FoodList", foodArrayList.toString())
                    Log.d("Cart", Cart.cart.toString())
                    Log.d("Ingredients", ingredients.toString())
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("Error", t.message.toString())
                loadingDialog.dismissDialog()
            }
        })

    }
}












//        val toolbar = view.findViewById<Toolbar>(R.id.foodSearchBar)
//        toolbar.menu.findItem(R.id.ingredientsCart).setOnMenuItemClickListener {
//            val intent = Intent(context, IngredientsCartActivity::class.java)
//            startActivity(intent)
//            true
//        }
//        toolbar.menu.findItem(R.id.favorite_food).setOnMenuItemClickListener {
//            val intent = Intent(context, FavoriteFoodActivity::class.java)
//            startActivity(intent)
//            true
//        }
//        toolbar.menu.findItem(R.id.favorite_food).setOnMenuItemClickListener {
//            Log.d("Cart", Cart.cart.toString())
//            true
//        }