package uz.mod.templatex.ui.checkout

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.utils.extension.clear
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.ConfirmResponse
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.utils.SingleLiveEvent

class CheckoutViewModel constructor(
    application: Application,
    repository: CheckoutRepository
) : AndroidViewModel(application) {

    val phone = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    private val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    private val isEmailValid: LiveData<Boolean> = Transformations.map(email) {
        !it.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    val navigator = SingleLiveEvent<Int>()

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
    val response = Transformations.switchMap(request) {
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

    fun getPhone() = phone.value!!

    fun next() {
        navigator.value = 1
    }
}
