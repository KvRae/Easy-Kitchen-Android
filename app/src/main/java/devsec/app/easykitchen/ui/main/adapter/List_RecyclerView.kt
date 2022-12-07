package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class List_RecyclerView:RecyclerView.Adapter<List_RecyclerView.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row,parent,false);
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.itemView
    }

    override fun getItemCount(): Int {
        return 10
    }
    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}