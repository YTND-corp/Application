package uz.uzmobile.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.uzmobile.templatex.model.remote.network.ApiResponse
import uz.uzmobile.templatex.model.remote.responce.CartResponse

interface CartService {

    @GET("v1/carts")
    fun getCart(): LiveData<ApiResponse<CartResponse>>

    @GET("v1/carts/products/{id}/remove")
    fun remove(@Path("id") id: Int): LiveData<ApiResponse<CartResponse>>

    @GET("v1/carts/products/{id}/quantity/{count}")
    fun updateCount(@Path("id") id: Int, @Path("count") count: Int): LiveData<ApiResponse<Any>>
}