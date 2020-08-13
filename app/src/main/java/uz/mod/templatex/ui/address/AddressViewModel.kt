package uz.mod.templatex.ui.address

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber
import uz.mod.templatex.model.local.entity.City
import uz.mod.templatex.model.local.entity.profile.ProfileAddress
import uz.mod.templatex.model.remote.request.StoreRequest
import uz.mod.templatex.model.remote.response.Delivery
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.utils.Event

class AddressViewModel constructor(application: Application, val repository: CheckoutRepository) :
    AndroidViewModel(application) {

    val city = MutableLiveData<City>()
    val street = MutableLiveData<String>()
    val home = MutableLiveData<String>()
    val flat = MutableLiveData<String>()
    val delivery = MutableLiveData<Delivery>()
    lateinit var phone: String


    private val loadUserAddressesFromDB = MutableLiveData<Boolean>()

    val userAddressesInServer = Transformations.switchMap(loadUserAddressesFromDB) {
        repository.getMyAddressFromServer()
    }

    fun setupWithDefaultAddress(addresses: List<ProfileAddress>?)  {
        var defaultAddress:ProfileAddress? = null

        addresses?.forEach {
            if (it.isDefault == true) defaultAddress = it
        }
        defaultAddress?.street?.apply {
            street.value = this
        }
        defaultAddress?.region?.apply {
            city.value = City(id, name)
        }
        defaultAddress?.building?.apply {
            home.value = this
        }

        defaultAddress?.entry?.apply {
            flat.value = this
        }
        Timber.e("$defaultAddress")
    }


    val isAllValid = MediatorLiveData<Boolean>().apply {
        fun validateFrom() {
            value = !city.value?.name.isNullOrEmpty()
                    && !street.value.isNullOrEmpty()
                    && !home.value.isNullOrEmpty()
                    && !flat.value.isNullOrEmpty()
                    && delivery.value != null
        }
        addSource(city) { validateFrom() }
        addSource(street) { validateFrom() }
        addSource(home) { validateFrom() }
        addSource(flat) { validateFrom() }
        addSource(delivery) { validateFrom() }
    }

    fun setArgs(args: AddressFragmentArgs) {
        phone = args.phone
        loadUserAddressesFromDB.value = true
    }

    val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.checkout()
    }

    val cities = Transformations.map(response) {
        Event(it.data)
    }

    fun getCities() {
        request.value = true
    }

    fun getDetails() = StoreRequest(phone, city.value, street.value, home.value, flat.value, null, delivery.value)

}