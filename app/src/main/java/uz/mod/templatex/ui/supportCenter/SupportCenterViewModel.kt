package uz.mod.templatex.ui.supportCenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.repository.profile.SupportCenterRepository

class SupportCenterViewModel constructor(application: Application, val repository: SupportCenterRepository): AndroidViewModel(application) {

    val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.getPages()
    }

    fun getPages() {
        request.value = true
    }
}