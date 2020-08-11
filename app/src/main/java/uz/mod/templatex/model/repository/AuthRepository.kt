package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.Prefs
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.api.AuthService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.AuthConfirmationResponse

class AuthRepository constructor(val service: AuthService, val prefs: Prefs) {

    init {
        Timber.d("Injection CarRepository")
    }

    fun signUp(
        name: String,
        surname: String?,
        email: String?,
        phone: String
    ): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?) = item
            override fun createCall() = service.signUp(name, surname, email, phone)
        }.asLiveData()
    }

    fun signIn(login: String): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                Timber.e("Login = $login")
                return service.signIn(login)
            }

        }.asLiveData()
    }

    fun confirm(code: String): LiveData<Resource<User>> {
        return object : NetworkOnlyResource<User, AuthConfirmationResponse>() {
            override fun processResult(item: AuthConfirmationResponse?): User? {
                prefs.userName = "${item?.user?.name} ${item?.user?.surname}"
                prefs.token = item?.meta?.token

                return item?.user
            }

            override fun createCall(): LiveData<ApiResponse<AuthConfirmationResponse>> {
                return service.confirm(code)
            }
        }.asLiveData()
    }

    fun logout() {
        prefs.userName = null
        prefs.token = null
    }

    fun getUserName() = prefs.userName

    fun getUserPhone() = prefs.phone

    fun isLoggedIn() = prefs.token != null
}