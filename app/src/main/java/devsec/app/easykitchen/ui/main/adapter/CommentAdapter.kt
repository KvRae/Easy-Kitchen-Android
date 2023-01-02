package devsec.app.easykitchen.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.data.models.Comment
import devsec.app.easykitchen.data.models.Recette
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CommentAdapter(private val commentsList: ArrayList<Comment>,private val idUser:String):RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

  class CommentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val comment_author = itemView.findViewById<TextView>(R.id.commentAuthor)
        val comment_author_image = itemView.findViewById<ImageView>(R.id.commentAuthorImage)
        val date_comment = itemView.findViewById<TextView>(R.id.dateComment)
        val comment_content = itemView.findViewById<TextView>(R.id.commentContent)
        val comment_idUser=itemView.findViewById<TextView>(R.id.comment_idUser)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.comment_idUser.visibility=View.GONE
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)

        val inputDate = comment.date
        val date = inputFormat.parse(inputDate)
        val outputDate = outputFormat.format(date!!)
        holder.comment_idUser.text=comment.user
        holder.comment_author.text = comment.username
        holder.date_comment.text = outputDate
        holder.comment_content.text = comment.text
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }
}
