package uz.uzmobile.templatex.ui.selection

import android.app.Application
import androidx.lifecycle.*
import uz.uzmobile.templatex.model.local.entity.HomeGender
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.repository.CategoryRepository

class SelectionViewModel constructor(application: Application, repository: CategoryRepository): AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    val response: LiveData<Resource<List<HomeGender>>> = Transformations.switchMap(request) {
        repository.getHome()
    }

    fun getHome() {
        request.value = true
    }
}