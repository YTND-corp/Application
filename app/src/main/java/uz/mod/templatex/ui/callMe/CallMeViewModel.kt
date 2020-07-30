package uz.mod.templatex.ui.callMe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.repository.profile.ProfileRepository
import uz.mod.templatex.utils.extension.clear

class CallMeViewModel constructor(application: Application, val repository: ProfileRepository) : AndroidViewModel(application) {

    val request = MutableLiveData<Boolean>()
    val phone = MutableLiveData<String>()
    val response = Transformations.switchMap(request) {
        repository.callback(phone.value!!)
    }
    val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    fun callback() {
        request.value = true
    }
}
