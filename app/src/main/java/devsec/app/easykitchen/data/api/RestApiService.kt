package devsec.app.easykitchen.api


import devsec.app.easykitchen.data.models.User
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
    @POST("users")
    fun addUser(@Body user: User): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<ResponseBody>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<ResponseBody>

    //***********************Blog***********************//

    //***********************Recipe***********************//

    //***********************Ingredient***********************//
    @GET("list.php?i=list")
    fun getIngredients(): Call<ResponseBody>



}

class RetrofitInstance {
    companion object {
        const val BASE_URL: String = "http://10.0.2.2:3000/api/"

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

