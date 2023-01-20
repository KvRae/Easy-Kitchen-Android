package devsec.app.easykitchen.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devsec.app.easykitchen.data.repositories.FileRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class FileViewModel(private val repository: FileRepository = FileRepository()):ViewModel() {


   suspend fun uploadFile(file: MultipartBody.Part) = repository.uploadFile(file)

    fun uploadFile(file: File) = viewModelScope.launch {
        viewModelScope.launch {
            repository.uploadFile(MultipartBody.Part.createFormData("file", file.name))
        }
    }
}
