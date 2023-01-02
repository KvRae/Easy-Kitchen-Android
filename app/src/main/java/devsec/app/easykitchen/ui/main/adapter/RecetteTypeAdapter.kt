package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class RecetteTypeAdapter(private val recetteTypeList: ArrayList<String>) : RecyclerView.Adapter<RecetteTypeAdapter.RecetteTypeViewHolder>() {
    private lateinit var mListener: RecetteTypeAdapter.OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetteTypeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.area_item, parent, false)
        return RecetteTypeViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: RecetteTypeViewHolder, position: Int) {
        val currentItem = recetteTypeList[position]
        holder.areaName.text = currentItem
    }

    override fun getItemCount() = recetteTypeList.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class RecetteTypeViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val areaName: TextView = itemView.findViewById(R.id.areaName)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}