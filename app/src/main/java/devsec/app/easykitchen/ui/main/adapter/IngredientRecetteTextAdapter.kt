package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class IngredientsRecetteTextAdapter(private val ingredientList: ArrayList<String>,private val measureList:ArrayList<String>):RecyclerView.Adapter<IngredientsRecetteTextAdapter.IngredientsRecetteTextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsRecetteTextHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_ingredient,parent,false);
        return IngredientsRecetteTextHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsRecetteTextHolder, position: Int) {
        val ingredientString = measureList[position]+" "+ingredientList[position]
        holder.ingredientName.text = ingredientString
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class IngredientsRecetteTextHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val ingredientName = itemView.findViewById<TextView>(R.id.ingredientReq)
    }
}