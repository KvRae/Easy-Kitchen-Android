package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class Categorie_RecyclerView:RecyclerView.Adapter<Categorie_RecyclerView.CategorieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categorie_row,parent,false);
        return CategorieViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {

        holder.itemView
    }

    override fun getItemCount(): Int {
        return 10
    }
    class CategorieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}