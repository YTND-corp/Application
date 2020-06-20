package uz.mod.templatex.ui.signIn

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.extension.backEndPhoneFormat
import uz.mod.templatex.extension.clear
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.AuthRepository

class SignInViewModel constructor(
    application: Application,
    repository: AuthRepository
) : AndroidViewModel(application) {

    val phone = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    val isPasswordValid: LiveData<Boolean> = Transformations.map(password) {
        !it.isNullOrEmpty() && it.clear.length >= 6
    }

    val isAllValid = MediatorLiveData<Boolean>()
        .apply {
            fun validateFrom() {
                value = isPhoneValid.value ?: false
                        && isPasswordValid.value ?: false
            }

            addSource(isPhoneValid) { validateFrom() }
            addSource(isPasswordValid) { validateFrom() }
        }

    val request = MutableLiveData<Boolean>()
    val responce: LiveData<Resource<User>> = Transformations.switchMap(request) {
        repository.signIn(
            phone.value?.backEndPhoneFormat()!!,
            password.value!!
        )
    }

    fun signIn() {
        request.value = true
    }
}