
package devsec.app.easykitchen.api

import devsec.app.easykitchen.data.models.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RestApiService {


    //*********************** Sign up/in ***********************//
    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(@Body info: User): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("register")
    fun registerUser(
        @Body info: User
    ): Call<ResponseBody>

    //*********************** User ***********************//
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("users/{id}")
    fun getUser(@Path("id") id: String): Call<User>

    @Headers("Content-Type:application/json")
    @PATCH("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<ResponseBody>

    //***********************comments***********************//

    @GET("recettes/{id}/comments")
    fun getCommentsByRecette(@Path("id")id:String):Call<List<Comment>>

    @POST("comments")
    fun postComment(
        @Body info: Comment
    ):Call<ResponseBody>




    //***********************Recipe***********************//
     @Headers("Content-Type:application/json")
    @GET("recettes")
    fun getRecette(): Call<List<Recette>>


    @Headers("Content-Type:application/json")
    @GET("recettes/{id}")
    fun getRecetteById(@Path("id") id: String): Call<Recette>

    @Headers("Content-Type:application/json")
    @POST("recettes")
    fun addRecette(
        @Body info: Recette
    ): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PATCH("recettes/{id}/like")
    fun likeRecette(
        @Path("id") id: String,
        @Body user:User

    ): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PATCH("recettes/{id}/dislike")
    fun dislikeRecette(
        @Path("id") id: String,
        @Body user:User
//        @Field("userId") userId:String

    ): Call<ResponseBody>

    @Multipart
    @POST("uploadfile")
    fun postImage(
        @Part image: MultipartBody.Part,
    ): Call<ResponseBody>

    //***********************Ingredient***********************//
    @GET("ingredients")
    fun getIngredientsList(): Call<List<Ingredients>>

    //***********************Category***********************//
    @GET("categories")
    fun getCategoriesList(): Call<List<Category>>
    //***********************Food***********************//
    @GET("food")
    fun getFoodsList(): Call<List<Food>>

    @GET("food/{id}")
    fun getFoodById(@Path("id") id: String): Call<Food>
    /*********************** Area ***********************/
    @GET("areas")
    fun getAreasList(): Call<List<Area>>




}

class RetrofitInstance {
    companion object {
//        const val BASE_URL: String = "http://10.0.2.2:3000/api/"
        const val BASE_URL: String = "http://192.168.1.20:3000/api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

