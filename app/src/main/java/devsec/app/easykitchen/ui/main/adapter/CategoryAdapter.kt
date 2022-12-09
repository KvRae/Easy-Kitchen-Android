package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Category

class CategoryAdapter(private val categoriesList: List<Category>):RecyclerView.Adapter<CategoryAdapter.CategorieViewHolder>() {
    class CategorieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val category_name = itemView.findViewById<TextView>(R.id.category_name)
        val category_image = itemView.findViewById<ImageView>(R.id.category_image)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categorie_row,parent,false);
        return CategorieViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        val category = categoriesList[position]
        holder.category_name.text = category.name
        Picasso.get().load(category.image).into(holder.category_image)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

}