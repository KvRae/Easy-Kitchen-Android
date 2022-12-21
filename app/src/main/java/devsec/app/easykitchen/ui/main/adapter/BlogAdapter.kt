package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Recette


class BlogAdapter(private val blogList: ArrayList<Recette>):RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    private lateinit var likeDiff: Number

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class BlogViewHolder(itemView: View ,listener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val blog_author = itemView.findViewById<TextView>(R.id.blogAuthor)
        val blog_author_image = itemView.findViewById<ImageView>(R.id.blogAuthorImage)
        val blog_upvote = itemView.findViewById<AppCompatButton>(R.id.upvote)
        val blog_duration = itemView.findViewById<TextView>(R.id.blog_time)
        val blog_title = itemView.findViewById<TextView>(R.id.blog_titre)


        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition)
                }
            }
        }

    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_item, parent, false)
        return BlogViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = blogList[position]
        val a=blog.likes
        val b=blog.dislikes
        likeDiff = ( a.toFloat()  - b.toFloat()).toInt()
        holder.blog_author.text = blog.user
        holder.blog_upvote.text = likeDiff.toString()
        holder.blog_duration.text = blog.duration.toString()
        holder.blog_title.text = blog.name
    }

    override fun getItemCount(): Int {
        return blogList.size
    }
}
