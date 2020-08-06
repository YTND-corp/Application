package uz.mod.templatex.ui.address

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.model.local.entity.City
import uz.mod.templatex.model.remote.request.StoreRequest
import uz.mod.templatex.model.remote.response.Delivery
import uz.mod.templatex.model.repository.CheckoutRepository

class AddressViewModel constructor(application: Application, val repository: CheckoutRepository) :
    AndroidViewModel(application) {

    val city = MutableLiveData<City>()
    val street = MutableLiveData<String>()
    val home = MutableLiveData<String>()
    val flat = MutableLiveData<String>()
    val delivery = MutableLiveData<Delivery>()
    lateinit var phone: String

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
    }

    val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.checkout()
    }

    val cities = Transformations.map(response) {
        it.data
    }

    fun getCities() {
        request.value = true
    }

    fun getDetails() = StoreRequest(phone, city.value, street.value, home.value, flat.value, null, delivery.value)

}