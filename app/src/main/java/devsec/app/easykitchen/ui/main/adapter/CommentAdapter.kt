package devsec.app.easykitchen.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Comment
import devsec.app.easykitchen.data.models.Recette


class CommentAdapter(private val commentsList: ArrayList<Comment>):RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

  class CommentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val comment_author = itemView.findViewById<TextView>(R.id.commentAuthor)
        val comment_author_image = itemView.findViewById<ImageView>(R.id.commentAuthorImage)
        val date_comment = itemView.findViewById<TextView>(R.id.dateComment)
        val comment_content = itemView.findViewById<TextView>(R.id.commentContent)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val comment = commentsList[position]
        holder.comment_author.text = comment.user
        holder.date_comment.text = comment.date
        holder.comment_content.text = comment.text
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }
}
