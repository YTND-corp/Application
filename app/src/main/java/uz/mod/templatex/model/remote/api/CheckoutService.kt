package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.CheckoutResponse
import uz.mod.templatex.model.remote.response.CheckoutUserResponse
import uz.mod.templatex.model.remote.response.ConfirmResponse
import uz.mod.templatex.model.remote.response.StoreResponse

interface CheckoutService {

    @GET("v1/checkout")
    fun checkOut(): LiveData<ApiResponse<CheckoutResponse>>

    @FormUrlEncoded
    @POST("v1/checkout/user")
    fun user(
        @Field("first_name") name: String,
        @Field("last_name") surname: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): LiveData<ApiResponse<ConfirmResponse>>

    @FormUrlEncoded
    @POST("v1/checkout/phone/confirm")
    fun confirm(
        @Field("phone") phone: String,
        @Field("code") email: String
    ): LiveData<ApiResponse<ConfirmResponse>>




    @FormUrlEncoded
    @POST("v1/checkout/store")
    fun store(
        @Field("address[region_id]") regionId: Int?,
        @Field("address[city]") region: String?,
        @Field("address[district]") street: String?,
        @Field("address[building]") home: String?,
        @Field("address[entry]") flat: String?,
        @Field("address[additional_info]") comment: String?,
        @Field("delivery[carrier_id]") carrierId: Int?,
        @Field("delivery[service_id]") carrierServiceId: Int?,
        @Field("delivery[date]") date: String?,
        @Field("delivery[time]") time: String?,
        @Field("payment[type]") paymentMethod: String?,
        @Field("payment[provider]") paymentProvider: String?
    ): LiveData<ApiResponse<StoreResponse>>

}