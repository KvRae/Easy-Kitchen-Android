package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class IngredientsTextAdapter(private val ingredientList: List<String>):RecyclerView.Adapter<IngredientsTextAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_ingredient,parent,false);
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.ingredientName.text = ingredientList[position]
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val ingredientName = itemView.findViewById<TextView>(R.id.ingredientReq)
    }
}