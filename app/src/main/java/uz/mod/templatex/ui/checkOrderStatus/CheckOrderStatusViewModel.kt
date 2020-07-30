package uz.mod.templatex.ui.checkOrderStatus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.repository.profile.ProfileRepository

class CheckOrderStatusViewModel constructor(application: Application, val repository: ProfileRepository) :
    AndroidViewModel(application) {
    val request = MutableLiveData<Boolean>()
    val reference = MutableLiveData<String>()
    val isReferenceValid: LiveData<Boolean> = Transformations.map(reference) {
        !it.isNullOrEmpty()
    }
    val response = Transformations.switchMap(request) {
        repository.checkOrderState(reference.value!!)
    }

    fun checkOrderStatus() {
        request.value = true
    }
}
