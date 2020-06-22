package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.responce.CheckoutUserResponse

interface CheckoutService {

    @GET("v1/checkout")
    fun checkOut(): LiveData<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("v1/checkout/user")
    fun user(
        @Field("first_name") name: String,
        @Field("last_name") surname: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): LiveData<ApiResponse<CheckoutUserResponse>>
}