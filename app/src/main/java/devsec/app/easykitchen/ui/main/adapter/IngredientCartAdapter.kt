package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.utils.services.Cart

class IngredientCartAdapter (private val ingredientsList: ArrayList<String>) : RecyclerView.Adapter<IngredientCartAdapter.IngredientsViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class IngredientsViewHolder(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val ingredient_name = itemView.findViewById<TextView>(R.id.ingredient_name)
        val ingredientItemIcon = itemView.findViewById<ImageButton>(R.id.ingredientItemIcon)

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)

        return IngredientsViewHolder(view,mListener)

    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.ingredient_name.text = ingredient
        holder.ingredientItemIcon.setImageResource(R.drawable.ic_baseline_remove_24)

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
}