package uz.mod.templatex.ui.askQuestion

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import uz.mod.templatex.model.repository.profile.ProfileRepository
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.extension.clear

class AskQuestionViewModel constructor(application: Application, val repository: ProfileRepository) :
    AndroidViewModel(application) {
    val VK_URL = Const.VK_URL
    val INSTAGRAM_URL = Const.INSTAGRAM_URL
    val TELEGRAM_URL = Const.TELEGRAM_URL
    val TIKTOK_URL = Const.TIKTOK_URL
    val FACEBOOK_URL = Const.FACEBOOK_URL

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val question = MutableLiveData<String>()

    private val isPhoneValid: LiveData<Boolean> = Transformations.map(phone) {
        !it.isNullOrEmpty() && it.clear.length == 13
    }
    private val isEmailValid: LiveData<Boolean> = Transformations.map(email) {
        !it.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }
    val isAllValid = MediatorLiveData<Boolean>()
        .apply {
            fun validateForm() {
                value = !name.value.isNullOrEmpty()
                        && isEmailValid.value ?: false
                        && isPhoneValid.value ?: false
                        && !question.value.isNullOrEmpty()
            }

            addSource(name) { validateForm() }
            addSource(isEmailValid) { validateForm() }
            addSource(isPhoneValid) { validateForm() }
            addSource(question) { validateForm() }
        }

    val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.askQuestion(name.value!!, email.value!!, phone.value!!, question.value!!)
    }

    fun askQuestion() {
        request.value = true
    }
}
