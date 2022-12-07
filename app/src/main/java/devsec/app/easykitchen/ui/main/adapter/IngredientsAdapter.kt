package devsec.app.easykitchen.ui.main.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R

class IngredientsAdapter(private val ingredientsList: List<String>) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>()
{
    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val ingredient_name = itemView.findViewById<TextView>(R.id.ingredient_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientsViewHolder(view)

    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.ingredient_name.text = ingredient

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
}