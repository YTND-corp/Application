package uz.mod.templatex.ui.signIn

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.utils.extension.backEndPhoneFormat
import uz.mod.templatex.utils.extension.clear
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.AuthRepository

class SignInViewModel constructor(
    application: Application,
    repository: AuthRepository
) : AndroidViewModel(application) {

    val phone = MutableLiveData<String>()

    val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    val request = MutableLiveData<Boolean>()
    val responce: LiveData<Resource<Any>> = Transformations.switchMap(request) {
        repository.signIn(
            phone.value?.backEndPhoneFormat()!!
        )
    }

    fun signIn() {
        request.value = true
    }
}