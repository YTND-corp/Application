package uz.mod.templatex.ui.profile.authorized.myAddresses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber
import uz.mod.templatex.model.repository.profile.MyAddressesRepository

class ProfileMyAddressesViewModel(application: Application, val repository: MyAddressesRepository) : AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()

    val response = Transformations.switchMap(request) {
        repository.getAddresses()
    }

    val isEmpty = Transformations.map(response) {
        it?.data?.isNullOrEmpty()
    }

    fun getMyAddresses() {
        Timber.e("Get my addresses")
        request.value = true
    }

    fun canEditAddress(id: Int) = repository.canEditAddress(id)
}