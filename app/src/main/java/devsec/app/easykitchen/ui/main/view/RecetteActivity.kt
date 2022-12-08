package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.RecettesInQueue
import devsec.app.easykitchen.ui.main.adapter.IngredientsTextAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecetteActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientsTextAdapter
    private lateinit var recette: RecettesInQueue.Recette
    private lateinit var recetteTitre: TextView
    private lateinit var timeTxt: TextView
    private lateinit var peopleTxt: TextView
    private lateinit var difficultyTxt: TextView
    private lateinit var instructionTxt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recette)

        recyclerView = findViewById(R.id.ingredientListView)
//        adapter = IngredientsTextAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        initRecette()
        recetteTitre = findViewById(R.id.foodRecipeName)
        timeTxt = findViewById(R.id.timeTxt)
        peopleTxt = findViewById(R.id.peopleTxt)
        difficultyTxt = findViewById(R.id.difficultyTxt)
        instructionTxt = findViewById(R.id.foodRecipeInstructionHeader)

        recetteTitre.text = recette.name
        timeTxt.text = recette.duration.toString()
        peopleTxt.text = recette.person.toString()
        difficultyTxt.text = recette.difficulty
        instructionTxt.text = recette.description


    }

    private fun initRecette() {

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)

        retIn.getRecetteById().enqueue(object : Callback<RecettesInQueue.Recette> {
            override fun onResponse(
                call: Call<RecettesInQueue.Recette>,
                response: Response<RecettesInQueue.Recette>
            ) {
                recette = response.body()!!
            }

            override fun onFailure(call: Call<RecettesInQueue.Recette>, t: Throwable) {
                Log.d("400", "Failure = " + t.toString());
            }


        })
    }
}