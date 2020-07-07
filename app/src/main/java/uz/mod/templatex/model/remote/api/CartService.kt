package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.*
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.CartResponse

interface CartService {

    @GET("v1/carts")
    fun getCart(): LiveData<ApiResponse<CartResponse>>

    @FormUrlEncoded
    @POST("v1/carts/products/remove")
    fun delete(@Field("products[]") ids: List<Int>): LiveData<ApiResponse<Any>>

    @GET("v1/carts/products/{id}/quantity/{count}")
    fun updateCount(@Path("id") id: Int, @Path("count") count: Int): LiveData<ApiResponse<Any>>

}