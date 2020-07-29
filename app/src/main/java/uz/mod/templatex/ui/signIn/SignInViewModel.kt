package uz.mod.templatex.ui.signIn

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.utils.extension.backEndPhoneFormat
import uz.mod.templatex.utils.extension.clear
import uz.mod.templatex.model.repository.AuthRepository
import uz.mod.templatex.utils.Event

class SignInViewModel constructor(
    application: Application,
    repository: AuthRepository
) : AndroidViewModel(application) {

    val phone = MutableLiveData<String>()

    val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }

    val request = MutableLiveData<Boolean>()
    val res = Transformations.switchMap(request) {
        repository.signIn(
            phone.value?.backEndPhoneFormat()!!
        )
    }

    val response = Transformations.map(res) {
        Event(it)
    }

    fun signIn() {
        request.value = true
    }
}