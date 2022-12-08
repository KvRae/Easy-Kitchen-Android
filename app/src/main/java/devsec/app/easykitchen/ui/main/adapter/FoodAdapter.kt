package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Food

class FoodAdapter(private val foodList: List<Food>): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    inner class FoodViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val food_name = itemView.findViewById<TextView>(R.id.foodName)
        val food_image = itemView.findViewById<ImageView>(R.id.foodImage)
//        val food_time = itemView.findViewById<TextView>(R.id.foodTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.food_name.text = food.name
        Picasso.get().load(food.image).into(holder.food_image)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}
