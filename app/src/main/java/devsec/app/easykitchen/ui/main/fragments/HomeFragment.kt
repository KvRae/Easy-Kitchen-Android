package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Category
import devsec.app.easykitchen.data.models.Food
import devsec.app.easykitchen.data.models.Recette
import devsec.app.easykitchen.ui.main.adapter.*
import devsec.app.easykitchen.ui.main.view.CategoryRecipesFilterActivity
import devsec.app.easykitchen.ui.main.view.FoodRecipeActivity
import devsec.app.easykitchen.ui.main.view.MainMenuActivity
import devsec.app.easykitchen.ui.main.view.RecetteActivity
import devsec.app.easykitchen.utils.services.LoadingDialog
import devsec.app.easykitchen.utils.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime

class HomeFragment : Fragment() {
    //************* Recommended food *************//
    private lateinit var recommendedFoodRecyclerView: RecyclerView
    private lateinit var recommendedFoodAdapter: BlogAdapter
    private lateinit var recetteArray:ArrayList<Recette>
    //************* Category **********************//
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    //************** Expert Recipes **************//
    private lateinit var expertRecipesRecyclerView: RecyclerView
    private lateinit var expertRecipesAdapter: FoodAdapter
    private lateinit var title: TextView
    //***************** Recycler View lists *************//
    private lateinit var recetteList: ArrayList<Recette>
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var foodList: ArrayList<Food>
    private lateinit var username: TextView
    lateinit var usernameString : String
    lateinit var sessionPref: SessionPref

    lateinit var user : HashMap<String, String>

    //***************** isBIO *******************//
    private lateinit var recommendedBioFoodRecyclerView: RecyclerView
    private lateinit var recommendedBioFoodAdapter: BlogBioAdapter
    private lateinit var saladeFoodRecyclerView: RecyclerView
    private lateinit var saladeFoodAdapter: SaladeFoodAdapter
    private lateinit var veganRecipesRecyclerView: RecyclerView
    private lateinit var veganRecipesAdapter: VeganFoodAdapter
    private lateinit var veganFoodList: ArrayList<Food>
    private lateinit var saladeFoodList: ArrayList<Food>
    private lateinit var recetteBioArray:ArrayList<Recette>

    private lateinit var healthy: MaterialButton
    private lateinit var noBioLL: LinearLayout
    private lateinit var bioLL: LinearLayout

    var bioCheck: Boolean = false


    //***************** Loading Dialog *************//
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navDrawerButton = view.findViewById<Button>(R.id.menu)
        username= view.findViewById(R.id.title_username)
        healthy= view.findViewById(R.id.healthy)
        noBioLL=view.findViewById(R.id.noIsBioLL)
        bioLL=view.findViewById(R.id.isBioLL)
        sessionPref = SessionPref(activity?.applicationContext!!)
        user = sessionPref.getUserPref()
        usernameString = user.get(SessionPref.USER_NAME).toString()

        val drawerLayout = (activity as MainMenuActivity).drawerLayout
        navDrawerButton.setOnClickListener {
            drawerLayout.open()
        }
        bioLL.visibility=View.GONE

        healthy.setOnClickListener {
        bioCheck=!bioCheck

            if (bioCheck) {
                bioLL.visibility=View.VISIBLE
                noBioLL.visibility=View.GONE
                healthy.setBackgroundColor(Color.parseColor("#94D794"))
                healthy.setTextColor(Color.WHITE)
            }else{
                noBioLL.visibility=View.VISIBLE
                bioLL.visibility=View.GONE
                healthy.setBackgroundColor(getResources().getColor(R.color.theWhite))
                healthy.setTextColor(getResources().getColor(R.color.blackText))
            }
        }

        //*********** Category Recycler View Implementation ***********//
        username.text=usernameString

            initCategoryList()
            val categoryLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            categoryRecyclerView = view.findViewById(R.id.categorieView)
            categoryRecyclerView.layoutManager = categoryLayoutManager
            categoryRecyclerView.setHasFixedSize(true)
            categoryAdapter = CategoryAdapter(categoryList)
            categoryRecyclerView.adapter = categoryAdapter

            categoryAdapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, CategoryRecipesFilterActivity::class.java)
                    intent.putExtra("category", categoryList[position].name)
                    startActivity(intent)
                }
            })

            //*********** Expert Recipes Recycler View Implementation ***********//
            initFoodList()
            val expertRecipesTextHeader = view.findViewById<TextView>(R.id.expertText)
            ("Expert Recipe for " + dailyFood()).also { expertRecipesTextHeader.text = it }
            val expertRecipesLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            expertRecipesRecyclerView = view.findViewById(R.id.expertView)
            expertRecipesRecyclerView.layoutManager = expertRecipesLayoutManager
            expertRecipesRecyclerView.setHasFixedSize(true)
            expertRecipesAdapter = FoodAdapter(foodList)
            expertRecipesRecyclerView.adapter = expertRecipesAdapter
            expertRecipesAdapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, FoodRecipeActivity::class.java)
                    intent.putExtra("id", foodList[position].id)
                    startActivity(intent)
                }
            })
            //************* Recommended food *************//

            initRecetteList()

            val recommendedLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recommendedFoodRecyclerView = view.findViewById(R.id.recommendedView)
            recommendedFoodRecyclerView.layoutManager = recommendedLayoutManager
            recommendedFoodAdapter = BlogAdapter(recetteArray)
            recommendedFoodRecyclerView.adapter = recommendedFoodAdapter

            recommendedFoodAdapter.setOnItemClickListener(object : BlogAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, RecetteActivity::class.java)
                    intent.putExtra("id", recetteArray[position].id)
                    startActivity(intent)
                }
            })

            initSaladeFoodList()
            val veganLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            saladeFoodRecyclerView = view.findViewById(R.id.saladeView)
            saladeFoodRecyclerView.layoutManager = veganLayoutManager
            saladeFoodRecyclerView.setHasFixedSize(true)
            saladeFoodAdapter = SaladeFoodAdapter(saladeFoodList)
            saladeFoodRecyclerView.adapter = saladeFoodAdapter

            saladeFoodAdapter.setOnItemClickListener(object : SaladeFoodAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, FoodRecipeActivity::class.java)
                    intent.putExtra("id", saladeFoodList[position].id)
                    startActivity(intent)
                }
            })

            //*********** Expert Recipes Recycler View Implementation ***********//
            initVeganFoodList()

            val veganRecipesLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            veganRecipesRecyclerView = view.findViewById(R.id.veganView)
            veganRecipesRecyclerView.layoutManager = veganRecipesLayoutManager
            veganRecipesRecyclerView.setHasFixedSize(true)
            veganRecipesAdapter = VeganFoodAdapter(veganFoodList)
            veganRecipesRecyclerView.adapter = veganRecipesAdapter
            veganRecipesAdapter.setOnItemClickListener(object : VeganFoodAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, FoodRecipeActivity::class.java)
                    intent.putExtra("id", veganFoodList[position].id)
                    startActivity(intent)
                }
            })
            //************* Recommended food *************//

            initBioRecetteList()

            val recommendedBioLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recommendedBioFoodRecyclerView = view.findViewById(R.id.recommendedBioView)
            recommendedBioFoodRecyclerView.layoutManager = recommendedBioLayoutManager
            recommendedBioFoodAdapter = BlogBioAdapter(recetteBioArray)
            recommendedBioFoodRecyclerView.adapter = recommendedBioFoodAdapter

            recommendedBioFoodAdapter.setOnItemClickListener(object : BlogBioAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, RecetteActivity::class.java)
                    intent.putExtra("id", recetteArray[position].id)
                    startActivity(intent)
                }
            })



    }

    //******************************** Init Recette List **********************************************//

    private fun initRecetteList() {
        recetteArray = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getRecette()
        call.enqueue(object : Callback<List<Recette>> {
            override fun onResponse(call: Call<List<Recette>>, response: Response<List<Recette>>) {
                if(response.body() != null) {
//                    recetteArray.addAll(response.body()!!)
                    val listRecette = response.body()!!.sortedWith(compareByDescending  { (it.likes.toFloat()-it.dislikes.toFloat()) })
                    recetteArray.addAll(listRecette)
                    recommendedFoodAdapter.notifyDataSetChanged()
                    Log.d("recetteArray",recetteArray.size.toString())

                }


            }

            override fun onFailure(call: Call<List<Recette>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }

        })
    }


    //****************************************** init Category **********************************//

    private fun initCategoryList() {
        categoryList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getCategoriesList()
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    for (category in categories!!) {
                        categoryList.add(category)
                        }
                    }
                categoryAdapter.notifyDataSetChanged()
                }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })
    }

    //****************************************** init food *********************************************//
    private fun initFoodList() {
        foodList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsList()
        call.enqueue((object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    var foods = response.body()
                    for (food in foods!!) {
                        if (dailyFood()=="Breakfast"){
                            if (food.category=="Breakfast"){ foodList.add(food)}
                        }
                        if (dailyFood()=="Lunch"){
                            if (food.category=="Lamb" || food.category=="Pasta" || food.category=="Beef"){ foodList.add(food)}
                        }
                        if (dailyFood()=="Dinner"){
                            if (food.category=="Side" ||food.category=="Starter"){ foodList.add(food)}
                        }
                    }
                    val randomFood = foodList.random()
                    foodList.clear()
                    foodList.add(randomFood)
                    expertRecipesAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        }))
    }
    //****************************************** init Vegan food *********************************************//
    private fun initSaladeFoodList() {
        saladeFoodList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsVegetarian()
        call.enqueue((object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    saladeFoodList.addAll(response.body()!!)
                    saladeFoodAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        }))
    }
    private fun initVeganFoodList() {
        veganFoodList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodsVegan()
        call.enqueue((object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    veganFoodList.addAll(response.body()!!)
                    expertRecipesAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        }))
    }


    //******************************** Init Bio Recette List **********************************************//

    private fun initBioRecetteList() {
        recetteBioArray = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getRecetteBio()
        call.enqueue(object : Callback<List<Recette>> {
            override fun onResponse(call: Call<List<Recette>>, response: Response<List<Recette>>) {
                if(response.body() != null) {
                    val listRecette = response.body()!!.sortedWith(compareByDescending  { (it.likes.toFloat()-it.dislikes.toFloat()) })
                    recetteBioArray.addAll(listRecette)
                    recommendedBioFoodAdapter.notifyDataSetChanged()
                    Log.d("recetteArray",recetteArray.size.toString())

                }


            }

            override fun onFailure(call: Call<List<Recette>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }

        })
    }
private fun reloadFragment(){
    activity?.recreate()
}

    private fun dailyFood (): String {
        val current = LocalTime.now()
        val morning = LocalTime.of(6,0)
        val afternoon = LocalTime.of(12,0)
        val evening = LocalTime.of(18,0)
        if (current.isAfter(morning) && current.isBefore(afternoon)){ return "Breakfast"}
        else if (current.isAfter(afternoon) && current.isBefore(evening)){ return "Lunch"}
        return "Dinner"

    }


}