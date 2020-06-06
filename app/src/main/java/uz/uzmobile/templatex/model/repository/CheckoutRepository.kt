package uz.uzmobile.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.aqlify.yonda.utils.Prefs
import uz.uzmobile.templatex.model.remote.api.CheckoutService
import uz.uzmobile.templatex.model.remote.network.ApiResponse
import uz.uzmobile.templatex.model.remote.network.NetworkOnlyResource
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.remote.responce.CheckoutUserResponse

class CheckoutRepository constructor(val service: CheckoutService, prefs: Prefs) {

    init {
        Timber.d("Injection CheckoutRepository")
    }

    fun user(
        name: String,
        surname: String,
        email: String,
        phone: String
    ): LiveData<Resource<Boolean>> {
        return object : NetworkOnlyResource<Boolean, CheckoutUserResponse>() {
            override fun processResult(item: CheckoutUserResponse?): Boolean? {
                return item?.confirmation
            }

            override fun createCall(): LiveData<ApiResponse<CheckoutUserResponse>> {
                return service.user(name, surname, email, phone)
            }

        }.asLiveData()
    }
}