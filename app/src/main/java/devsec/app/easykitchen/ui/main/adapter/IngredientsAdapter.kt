package devsec.app.easykitchen.ui.main.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class IngredientsAdapter(private var ingredientsList: List<String>) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>()
{
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class IngredientsViewHolder(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val ingredient_name = itemView.findViewById<TextView>(R.id.ingredient_name)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition)
                }
            }
        }
    }

    fun setRemoveButton(itemView:View){
            val text = itemView.findViewById<TextView>(R.id.ingredient_name).setTextColor(itemView.resources.getColor(
                androidx.constraintlayout.widget.R.color.material_grey_300))
            val icon = itemView.findViewById<ImageButton>(R.id.ingredientItemIcon).setImageResource(R.drawable.ic_baseline_remove_24)
    }

    fun setAddButton(itemView: View){
        val text = itemView.findViewById<TextView>(R.id.ingredient_name).setTextColor(itemView.resources.getColor(
            androidx.constraintlayout.widget.R.color.material_grey_900))
        val icon = itemView.findViewById<ImageButton>(R.id.ingredientItemIcon).setImageResource(R.drawable.ic_baseline_add_24)
    }



    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)

        return IngredientsViewHolder(view,mListener)

    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.ingredient_name.text = ingredient
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun filterList(filteredList: ArrayList<String>) {
        ingredientsList = filteredList
        notifyDataSetChanged()

    }
}