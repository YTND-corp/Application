package uz.mod.templatex.ui.profile.authorized.myData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.model.repository.profile.MyDataRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfileMyDataViewModel(application: Application, val repository: MyDataRepository) :
    AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    var selectedGender: String? = null
    val fullName = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()
    val isNotificationEnabled = MutableLiveData<Boolean>()
    val isSubscriptionEnabled = MutableLiveData<Boolean>()

    val response = Transformations.switchMap(request) {
        repository.getUserInfo()
    }

    val isEmpty = Transformations.map(response) {
        it.status == Status.ERROR
    }

    fun getUserInfo() {
        request.value = true
    }

    fun updateUserInfo(): LiveData<Resource<Any>> {
        var firstName: String? = null
        var lastName: String? = null
        val birthday =
            LocalDate.parse(
                this.birthday.value,
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        fullName.value?.trim()?.split(" ")?.forEachIndexed { index, s ->
            when (index) {
                0 -> firstName = s
                1 -> lastName = s
                else -> lastName.plus(s)
            }
        }

        return repository.updateUserInfo(
            firstName,
            lastName,
            phone.value,
            email.value,
            birthday,
            selectedGender,
            if (isNotificationEnabled.value == true) 1 else 0,
            if (isSubscriptionEnabled.value == true) 1 else 0
        )
    }
}