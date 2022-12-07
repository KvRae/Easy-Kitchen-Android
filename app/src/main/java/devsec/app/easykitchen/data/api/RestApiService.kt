package devsec.app.easykitchen.data.api


import devsec.app.easykitchen.data.models.ImageResponse
import devsec.app.easykitchen.data.models.RecettesInQueue
import devsec.app.easykitchen.data.models.User
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RestApiService {


    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(@Body info: User): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("register")
    fun registerUser(
        @Body info: User
    ): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("recettes")
    fun getRecette(): Call<RecettesInQueue>


    @Headers("Content-Type:application/json")
    @GET("recettes/637b87f3ecd1532b6052cbee")
    fun getRecetteById(): Call<RecettesInQueue.Recette>

    @Headers("Content-Type:application/json")
    @POST("recettes")
    fun addRecette(
        @Body info: RecettesInQueue.Recette
    ): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @Multipart
    @POST("uploadfile")
    fun postImage(
        @Part image: MultipartBody.Part,
//        @Part("myFile") name: RequestBody?
    ): Call<ResponseBody>

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

