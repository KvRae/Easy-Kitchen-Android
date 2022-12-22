package devsec.app.easykitchen.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Comment
import devsec.app.easykitchen.data.models.Recette
import devsec.app.easykitchen.data.models.User
import devsec.app.easykitchen.ui.main.adapter.BlogAdapter
import devsec.app.easykitchen.ui.main.adapter.CommentAdapter
import devsec.app.easykitchen.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecetteActivity : AppCompatActivity() {

//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: IngredientsTextAdapter
    private lateinit var adapter : CommentAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var recetteTitre: TextView
    private lateinit var timeTxt: TextView
    private lateinit var peopleTxt: TextView
    private lateinit var difficultyTxt: TextView
    private lateinit var instructionTxt: TextView
    private lateinit var recetteUser:TextView
    private lateinit var recetteLikes:TextView
    private lateinit var likeButton:AppCompatImageButton
    private lateinit var dislikeButton:AppCompatImageButton
    lateinit var commentInput:EditText
//    lateinit var recette: Recette
    lateinit var sessionPref: SessionPref
    lateinit var idUser : String
    lateinit var idRecette : String
    lateinit var submitComment:AppCompatButton
    lateinit var user : HashMap<String, String>
//    lateinit var userName:String
    lateinit var commentsList:ArrayList<Comment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recette)
        sessionPref = SessionPref(this)

        idRecette = intent.getStringExtra("id").toString()

        user = sessionPref.getUserPref()
        idUser = user.get(SessionPref.USER_ID).toString()
//        recette = Recette()
        initRecette()
//        Log.d("checkRecette",recette.user)
//        getUser()
        initComments()
//        recyclerView = findViewById(R.id.ingredientListView)
////
////        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
////        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)

        recyclerView = findViewById(R.id.commentSession)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CommentAdapter(commentsList)
        recyclerView.adapter = adapter

        commentInput = findViewById(R.id.commentEditText)
        recetteTitre = findViewById(R.id.foodRecipeName)
        timeTxt = findViewById(R.id.timeTxt)
        peopleTxt = findViewById(R.id.peopleTxt)
        difficultyTxt = findViewById(R.id.difficultyTxt)
        instructionTxt = findViewById(R.id.foodRecipeInstructions)
        recetteUser = findViewById(R.id.foodRecipeCategory)
        likeButton = findViewById(R.id.upvoteButton)
        dislikeButton = findViewById(R.id.downvoteButton)
        recetteLikes=findViewById(R.id.recetteLikes)
        submitComment=findViewById(R.id.submitComment)
        //implementation//
//
//        recetteTitre.text = recette.name
//        timeTxt.text = recette.duration.toString()
//        peopleTxt.text = recette.person.toString()
//        difficultyTxt.text = recette.difficulty
//        instructionTxt.text =recette.description
//        recetteUser.text = userName


        likeButton.setOnClickListener{
            likeRecette()
            initRecette()
        }
        dislikeButton.setOnClickListener{
            dislikeRecette()
            initRecette()
        }
        submitComment.setOnClickListener {
        if (commentInput.text.isEmpty()){
            commentInput.error = "comment is required"
            commentInput.requestFocus()
        }else{
            postComment(commentInput.text.toString())
            initComments()
        }

        }

    }
    private fun initRecette() {
//        recette=Recette()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getRecetteById(idRecette)
        call.enqueue(object : Callback<Recette> {
            override fun onResponse(
                call: Call<Recette>,
                response: Response<Recette>
            ) {
//                recette = Recette(response.body()!!.id,
//                    response.body()!!.name,
//                    response.body()!!.description,
//                    response.body()!!.image,
//                    response.body()!!.isBio,
//                    response.body()!!.duration,
//                    response.body()!!.person,
//                    response.body()!!.difficulty,
//                    response.body()!!.user,
//                    response.body()!!.comments,
//                    response.body()!!.likes,
//                    response.body()!!.dislikes,
//                    response.body()!!.usersLiked,
//                    response.body()!!.usersDisliked
//                )
//                recetteLikes.text = (recette.likes.toFloat() - recette.dislikes.toFloat()).toInt().toString()

                recetteTitre.text = response.body()!!.name
                timeTxt.text = response.body()!!.duration.toString()
                peopleTxt.text = response.body()!!.person.toString()
                difficultyTxt.text = response.body()!!.difficulty
                instructionTxt.text =response.body()!!.description
                recetteUser.text = response.body()!!.user
                recetteLikes.text = (response.body()!!.likes.toFloat() - response.body()!!.dislikes.toFloat()).toInt().toString()


            }

            override fun onFailure(call: Call<Recette>, t: Throwable) {
                Log.d("400", "Failure = " + t.toString());
            }


        })
    }
    private fun likeRecette() {
        val user = User(id=idUser)
        Log.d("aaaaa","aaaaaaaaaaaaaa")
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.likeRecette(user)
        call.enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RecetteActivity, response.body()!!.toString(), Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })
    }

    private fun dislikeRecette() {
        val user = User(id=idUser)
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.dislikeRecette(user)
        call.enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RecetteActivity, response.body()!!.toString(), Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })
    }
//    private fun getUser() {
//        userName=""
//        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
//        val call = retIn.getUser(recette.user)
//        call.enqueue(object: Callback<User>{
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                userName=response.body()!!.username
//            }
//
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Log.d("400","Failure = "+t.toString());
//            }
//
//
//        })
//    }

    private fun initComments() {
        commentsList = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getCommentsByRecette(idRecette)
        call.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                commentsList.addAll(response.body()!!)
                adapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })
    }

    private fun postComment(commentText:String) {

        val commentInfo = Comment(
            commentText,
            idRecette,
            idUser
        )
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.postComment(commentInfo)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Toast.makeText(this@RecetteActivity, "comment Added", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(this@RecetteActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
                }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("400","Failure = "+t.toString());
            }

        })
    }

}