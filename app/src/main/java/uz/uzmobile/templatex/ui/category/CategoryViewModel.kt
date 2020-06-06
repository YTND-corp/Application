package uz.uzmobile.templatex.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.uzmobile.templatex.model.local.entity.CategoryGender
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.repository.CategoryRepository

class CategoryViewModel constructor(application: Application, repository: CategoryRepository): AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    val response: LiveData<Resource<List<CategoryGender>>> = Transformations.switchMap(request) {
        repository.getCategories()
    }

    fun getCatalogs() {
        request.value = true
    }
}
