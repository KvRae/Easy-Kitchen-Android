package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class Expert_RecyclerView:RecyclerView.Adapter<Expert_RecyclerView.ExpertViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_row,parent,false);
        return ExpertViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertViewHolder, position: Int) {

        holder.itemView
    }

    override fun getItemCount(): Int {
        return 10
    }
    class ExpertViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}