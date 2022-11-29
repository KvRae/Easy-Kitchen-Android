package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class Recommended_RecyclerView:RecyclerView.Adapter<Recommended_RecyclerView.RecommendedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_row,parent,false);
        return RecommendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {

        holder.itemView
    }

    override fun getItemCount(): Int {
        return 25
    }
    class RecommendedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}