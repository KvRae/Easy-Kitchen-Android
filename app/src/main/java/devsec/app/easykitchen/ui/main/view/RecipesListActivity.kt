package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.adapter.RecipesAdapter

class RecipesListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes_list)

        recyclerView = findViewById(R.id.listView)
        adapter = RecipesAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }
}