package devsec.app.easykitchen.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Recette
import devsec.app.easykitchen.data.models.User
import devsec.app.easykitchen.ui.main.adapter.BlogAdapter
import devsec.app.easykitchen.ui.main.view.RecetteActivity
import devsec.app.easykitchen.ui.main.view.RecetteFormActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BlogFragment : Fragment() {

    private lateinit var adapter : BlogAdapter
    private lateinit var recyclerView : RecyclerView

    lateinit var formButton: FloatingActionButton

    private lateinit var recetteArray: ArrayList<Recette>






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        initBlogList()

        recyclerView = view.findViewById(R.id.blogList)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        adapter = BlogAdapter(recetteArray)
        recyclerView.adapter = adapter


        adapter.setOnItemClickListener(object : BlogAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, RecetteActivity::class.java)
                intent.putExtra("id", recetteArray[position].id)
                startActivity(intent)
            }
        })

        formButton= view.findViewById(R.id.formButton)
        formButton.setOnClickListener {
            val intent = Intent(context, RecetteFormActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


    }

    private fun initBlogList() {
        recetteArray = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getRecette()
        call.enqueue(object : Callback<List<Recette>> {
            override fun onResponse(call: Call<List<Recette>>, response: Response<List<Recette>>) {
                if(response.body() != null) {
//                    recetteArray.addAll(response.body()!!)
                    val listRecette = response.body()!!.sortedWith(compareByDescending  { (it.likes.toFloat()-it.dislikes.toFloat()) })
                    recetteArray.addAll(listRecette)
                    adapter.notifyDataSetChanged()

                    Log.d("recetteArray",recetteArray.size.toString())

                }


            }

            override fun onFailure(call: Call<List<Recette>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }

        })
    }
}