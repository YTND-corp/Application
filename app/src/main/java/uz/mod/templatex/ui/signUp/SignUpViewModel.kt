package uz.mod.templatex.ui.signUp

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import uz.mod.templatex.utils.extension.backEndPhoneFormat
import uz.mod.templatex.utils.extension.clear
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.AuthRepository
import uz.mod.templatex.utils.Event

class SignUpViewModel constructor(
    application: Application, repository: AuthRepository
) : AndroidViewModel(application) {

    val phone = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    val isEmailValid: LiveData<Boolean> = Transformations.map(email) {
        !it.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    val isAllValid = MediatorLiveData<Boolean>()
        .apply {
            fun validateFrom() {
                value = isPhoneValid.value ?: false
                        && !name.value.isNullOrEmpty()
            }

            addSource(isPhoneValid) { validateFrom() }
            addSource(name) { validateFrom() }
            addSource(surname) { validateFrom() }
            addSource(isEmailValid) { validateFrom() }
        }

    val request = MutableLiveData<Boolean>()
    val res: LiveData<Resource<Any>> = Transformations.switchMap(request) {
        repository.signUp(
            name.value!!,
            surname.value!!,
            email.value!!,
            phone.value?.backEndPhoneFormat()!!
        )
    }

    val response = Transformations.map(res) {
        Event(it)
    }

    fun signUp() {
        request.value = true
    }
}