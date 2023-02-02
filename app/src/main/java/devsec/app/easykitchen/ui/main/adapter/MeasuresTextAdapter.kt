package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class MeasuresTextAdapter(private val measuresList: List<String>  ): RecyclerView.Adapter<MeasuresTextAdapter.MeasuresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasuresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_mesures,parent,false);
        return MeasuresViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeasuresViewHolder, position: Int) {
        holder.measureName.text = measuresList[position]
    }

    override fun getItemCount(): Int {
        return measuresList.size
    }

    class MeasuresViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val measureName = itemView.findViewById<TextView>(R.id.mesuresReq)
    }

}
