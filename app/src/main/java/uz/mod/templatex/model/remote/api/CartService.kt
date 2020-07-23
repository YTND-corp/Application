package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.CartResponse

interface CartService {

    @GET("v1/carts")
    fun getCart(): LiveData<ApiResponse<CartResponse>>

    @GET("v1/carts/products/{id}/remove")
    fun delete(@Path("id") id: Int): LiveData<ApiResponse<Any>>

    @GET("v1/carts/products/{id}/quantity/{count}")
    fun updateCount(@Path("id") id: Int, @Path("count") count: Int): LiveData<ApiResponse<Any>>

}