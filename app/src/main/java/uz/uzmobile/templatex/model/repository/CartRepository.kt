package uz.uzmobile.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.uzmobile.templatex.model.remote.api.CartService
import uz.uzmobile.templatex.model.remote.network.ApiResponse
import uz.uzmobile.templatex.model.remote.network.NetworkOnlyResource
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.remote.responce.CartResponse

class CartRepository constructor(val service: CartService) {

    init {
        Timber.d("Injection CartRepository")
    }


    fun getCart(): LiveData<Resource<CartResponse>> {
        return object : NetworkOnlyResource<CartResponse, CartResponse>() {
            override fun processResult(item: CartResponse?): CartResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<CartResponse>> {
                return service.getCart()
            }

        }.asLiveData()
    }

    fun remove(id: Int): LiveData<Resource<CartResponse>> {
        return object : NetworkOnlyResource<CartResponse, CartResponse>() {
            override fun processResult(item: CartResponse?): CartResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<CartResponse>> {
                return service.remove(id)
            }

        }.asLiveData()
    }

    fun updateProductCount(id: Int, count: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.updateCount(id, count)
            }

        }.asLiveData()
    }
}