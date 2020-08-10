package uz.mod.templatex.model.repository.profile

import androidx.lifecycle.LiveData
import uz.mod.templatex.model.local.Prefs
import uz.mod.templatex.model.remote.api.profile.MyDataService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.profile.MyDataResponse

class MyDataRepository(
    val service: MyDataService,
    val prefs: Prefs
) {
    fun getUserInfo(): LiveData<Resource<MyDataResponse>> {
        return object : NetworkOnlyResource<MyDataResponse, MyDataResponse>() {
            override fun processResult(item: MyDataResponse?): MyDataResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<MyDataResponse>> {
                return service.getUserInfo()
            }
        }.asLiveData()
    }

    fun saveUsername(userName : String?) {
        prefs.userName = userName
    }

    fun updateUserInfo(
        firstName: String?,
        lastName: String?,
        phone: String?,
        email: String?,
        birthday: String?,
        gender: String?,
        notifications: Int?,
        subscriptions: Int?
    ): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.updateUserInfo(
                    firstName,
                    lastName,
                    phone,
                    email,
                    birthday,
                    gender,
                    notifications,
                    subscriptions
                )
            }
        }.asLiveData()
    }
}