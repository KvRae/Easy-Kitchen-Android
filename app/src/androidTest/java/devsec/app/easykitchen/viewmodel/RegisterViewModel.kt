package devsec.app.easykitchen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)

    fun register() {
//        GlobalScope.launch {
//            val response = retIn.registerUser()
//            if (response.isSuccessful) {
//                println("Response: ${response.body()}")
//            } else {
//                println("Response: ${response.errorBody()}")
//            }



        }
    }