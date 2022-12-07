package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Blog


class BlogAdapter(private val blogList: List<Blog>):RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {
    inner class BlogViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val blog_author = itemView.findViewById<TextView>(R.id.blogAuthor)
        val blog_author_image = itemView.findViewById<ImageView>(R.id.blogAuthorImage)
        val blog_image = itemView.findViewById<ImageView>(R.id.blogImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_item, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = blogList[position]
        holder.blog_author.text = blog.author
        holder.blog_author_image.setImageResource(blog.author_image)
        holder.blog_image.setImageResource(blog.image)
    }

    override fun getItemCount(): Int {
        return blogList.size
    }
}
