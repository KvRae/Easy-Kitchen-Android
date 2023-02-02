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
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class CategorieViewHolder(itemView: View, mListener:OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        val category_name = itemView.findViewById<TextView>(R.id.areaName)
        val category_image = itemView.findViewById<ImageView>(R.id.category_image)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categorie_row,parent,false)
        return CategorieViewHolder(view,mListener)
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