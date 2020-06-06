package uz.uzmobile.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.aqlify.yonda.utils.Prefs
import uz.uzmobile.templatex.model.local.entity.User
import uz.uzmobile.templatex.model.remote.api.AuthService
import uz.uzmobile.templatex.model.remote.network.ApiResponse
import uz.uzmobile.templatex.model.remote.network.NetworkOnlyResource
import uz.uzmobile.templatex.model.remote.network.Resource

class AuthRepository constructor(val service: AuthService, val  prefs: Prefs) {

    init {
        Timber.d("Injection CarRepository")
    }

    fun signUp(
        name: String,
        surname: String,
        email: String,
        phone: String,
        password: String
    ): LiveData<Resource<User>> {
        return object : NetworkOnlyResource<User, User>() {
            override fun processResult(item: User?): User? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<User>> {
                return service.signUp(name, surname, email, phone, password)
            }

        }.asLiveData()
    }

    fun signIn(login: String, password: String): LiveData<Resource<User>> {
        return object : NetworkOnlyResource<User, User>() {
            override fun processResult(item: User?): User? {
                prefs.userName = "${item?.name} ${item?.surname}"
                prefs.token = item?.token

                return item
            }

            override fun createCall(): LiveData<ApiResponse<User>> {
                return service.signIn(login, password)
            }

        }.asLiveData()
    }

    fun getUserName() = prefs.userName
    fun getUserPhone() = prefs.phone
    fun isLoggedIn() = prefs.token!=null


}