package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Recette

class RecommendedFoodAdapter(private val recetteList: ArrayList<Recette>):RecyclerView.Adapter<RecommendedFoodAdapter.RecommendedViewHolder>() {
    class RecommendedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val recette_name = itemView.findViewById<TextView>(R.id.titre)
        val recette_duration = itemView.findViewById<TextView>(R.id.time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_row,parent,false);
        return RecommendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val recette = recetteList[position]
        holder.recette_name.text=recette.name
        holder.recette_duration.text=recette.duration.toString()
    }

    override fun getItemCount(): Int {
        return recetteList.size
    }


}