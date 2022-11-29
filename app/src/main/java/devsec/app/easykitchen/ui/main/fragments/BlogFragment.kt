package devsec.app.easykitchen.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.ui.main.adapter.BlogAdapter
import devsec.app.easykitchen.data.models.Blog


class BlogFragment : Fragment() {

    private lateinit var adapter : BlogAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var blogArrayList: ArrayList<Blog>

    lateinit var blogImage : IntArray
    lateinit var blogAuthorImage : IntArray
    lateinit var blogAuthor : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBlogList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.blogList)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = BlogAdapter(blogArrayList)
        recyclerView.adapter = adapter
    }

    private fun initBlogList(){
        blogImage = intArrayOf(R.drawable.picture, R.drawable.picture, R.drawable.picture)
        blogAuthorImage = intArrayOf(R.drawable.android_icon, R.drawable.android_icon, R.drawable.android_icon, R.drawable.android_icon, R.drawable.android_icon, R.drawable.android_icon)
        blogAuthor = arrayOf("John Doe", "Jane Doe", "John Doe", "Jane Doe", "John Doe", "Jane Doe")

        blogArrayList = ArrayList()
        for(i in blogImage.indices){
            val blog = Blog(blogAuthor[i], blogAuthorImage[i],blogImage[i] )
            blogArrayList.add(blog)
        }



    }

}