package devsec.app.easykitchen.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(val username: LiveData<String>, val password: String) : ViewModel() {

    fun login(){}
}