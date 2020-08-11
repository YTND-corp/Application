package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.Prefs
import uz.mod.templatex.model.local.entity.City
import uz.mod.templatex.model.remote.api.CheckoutService
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.CheckoutResponse
import uz.mod.templatex.model.remote.response.ConfirmResponse
import uz.mod.templatex.model.remote.response.StoreResponse

class CheckoutRepository constructor(val service: CheckoutService, prefs: Prefs) {

    init {
        Timber.d("Injection CheckoutRepository")
    }

    fun user(
        name: String,
        surname: String?,
        email: String?,
        phone: String
    ): LiveData<Resource<ConfirmResponse>> = object : NetworkOnlyResource<ConfirmResponse, ConfirmResponse>() {
        override fun processResult(item: ConfirmResponse?) = item
        override fun createCall() = service.user(name, surname, email, phone)
    }.asLiveData()

    fun confirm(phone: String, code: String): LiveData<Resource<ConfirmResponse>> {
        return object : NetworkOnlyResource<ConfirmResponse, ConfirmResponse>() {
            override fun processResult(item: ConfirmResponse?) = item
            override fun createCall() = service.confirm(phone, code)
        }.asLiveData()
    }

    fun checkout(): LiveData<Resource<List<City>>> {
        return object : NetworkOnlyResource<List<City>, CheckoutResponse>() {
            override fun processResult(item: CheckoutResponse?) = item?.cities
            override fun createCall() = service.checkOut()
        }.asLiveData()
    }

    fun store(
        regionId: Int?,
        region: String?,
        street: String?,
        home: String?,
        flat: String?,
        comment: String?,
        carrierId: Int?,
        carrierServiceId: Int?,
        date: String?,
        time: String?,
        paymentMethod: String?,
        paymentProvider: String?
    ): LiveData<Resource<StoreResponse>> {
        return object : NetworkOnlyResource<StoreResponse, StoreResponse>() {
            override fun processResult(item: StoreResponse?) = item
            override fun createCall() = service.store(
                regionId,
                region,
                street,
                home,
                flat,
                comment,
                carrierId,
                carrierServiceId,
                date,
                time,
                paymentMethod,
                paymentProvider
            )
        }.asLiveData()
    }
}