package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class AreaAdapter(private val areaList: ArrayList<String>) : RecyclerView.Adapter<AreaAdapter.AreaViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.area_item, parent, false)
        return AreaViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val currentItem = areaList[position]
        holder.areaName.text = currentItem
    }

    override fun getItemCount() = areaList.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class AreaViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val areaName: TextView = itemView.findViewById(R.id.areaName)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }






}