package uz.mod.templatex.ui.signUp

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import uz.mod.templatex.utils.extension.backEndPhoneFormat
import uz.mod.templatex.utils.extension.clear
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.AuthRepository

class SignUpViewModel constructor(
    application: Application, repository: AuthRepository
) : AndroidViewModel(application) {

    val phone = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val repeat = MutableLiveData<String>()

    val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    val isEmailValid: LiveData<Boolean> = Transformations.map(email) {
        !it.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    val isPasswordValid: LiveData<Boolean> = Transformations.map(password) {
        !it.isNullOrEmpty() && it.clear.length >= 6
    }


    val isRepeatValid = MediatorLiveData<Boolean>()
        .apply {
            fun validateFrom() {
                value = isPasswordValid.value ?: false
                        && !repeat.value.isNullOrEmpty()
                        && repeat.value!!.clear.length >= 6
                        && repeat.value.equals(password.value)
            }

            addSource(isPasswordValid) { validateFrom() }
            addSource(repeat) { validateFrom() }
        }

    val isAllValid = MediatorLiveData<Boolean>()
        .apply {
            fun validateFrom() {
                value = isPhoneValid.value ?: false
                        && !name.value.isNullOrEmpty()
                        && !surname.value.isNullOrEmpty()
                        && isEmailValid.value ?: false
                        && !address.value.isNullOrEmpty()
                        && isPasswordValid.value ?: false
                        && isRepeatValid.value ?: false
            }

            addSource(isPhoneValid) { validateFrom() }
            addSource(name) { validateFrom() }
            addSource(surname) { validateFrom() }
            addSource(isEmailValid) { validateFrom() }
            addSource(address) { validateFrom() }
            addSource(isPasswordValid) { validateFrom() }
            addSource(isRepeatValid) { validateFrom() }
        }

    val request = MutableLiveData<Boolean>()
    val responce: LiveData<Resource<User>> = Transformations.switchMap(request) {
        repository.signUp(
            name.value!!,
            surname.value!!,
            email.value!!,
            phone.value?.backEndPhoneFormat()!!,
            password.value!!
        )
    }

    fun signUp() {
        request.value = true
    }
}