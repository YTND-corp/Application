package uz.mod.templatex.ui.selection

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.model.local.entity.HomeGender
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.CategoryRepository

class SelectionViewModel constructor(application: Application, repository: CategoryRepository): AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    val response: LiveData<Resource<List<HomeGender>>> = Transformations.switchMap(request) {
        repository.getHome()
    }

    fun getHome() {
        request.value = true
    }
}