package uz.uzmobile.templatex.ui.checkout

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import uz.uzmobile.templatex.extension.backEndPhoneFormat
import uz.uzmobile.templatex.extension.clear
import uz.uzmobile.templatex.model.local.entity.User
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.repository.CheckoutRepository

class CheckoutViewModel constructor(application: Application, repository: CheckoutRepository): AndroidViewModel(application) {

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
                        && !surname.value.isNullOrEmpty()
                        && isEmailValid.value ?: false
            }

            addSource(isPhoneValid) { validateFrom() }
            addSource(name) { validateFrom() }
            addSource(surname) { validateFrom() }
            addSource(isEmailValid) { validateFrom() }
        }

    val request = MutableLiveData<Boolean>()
    val responce: LiveData<Resource<Boolean>> = Transformations.switchMap(request) {
        repository.user(
            name.value!!,
            surname.value!!,
            email.value!!,
            phone.value?.clear!!
        )
    }

    fun user() {
        request.value = true
    }
}
