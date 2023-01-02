package devsec.app.easykitchen.data.repositories

import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import okhttp3.MultipartBody

class FileRepository {

    suspend fun uploadFile(file: MultipartBody.Part) = RetrofitInstance.getRetrofitInstance()
        .create(RestApiService::class.java).uploadImage(file)

}