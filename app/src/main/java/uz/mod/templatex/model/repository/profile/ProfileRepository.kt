package uz.mod.templatex.model.repository.profile

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.remote.api.profile.ProfileService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.profile.CheckOrderStateResponse

class ProfileRepository(val service: ProfileService) {
    init {
        Timber.d("Injection ProfileRepository")
    }

    fun callback(phone: String): LiveData<Resource<Any>> {
        return object :
            NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.callback(phone)
            }
        }.asLiveData()
    }

    fun checkOrderState(reference: String): LiveData<Resource<CheckOrderStateResponse>> {
        return object :
            NetworkOnlyResource<CheckOrderStateResponse, CheckOrderStateResponse>() {
            override fun processResult(item: CheckOrderStateResponse?): CheckOrderStateResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<CheckOrderStateResponse>> {
                return service.checkOrderState(reference)
            }
        }.asLiveData()
    }

    fun askQuestion(name: String, email: String, phone: String, question: String): LiveData<Resource<Any>> {
        return object :
            NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.askQuestion(name, email, phone, question)
            }
        }.asLiveData()
    }
}