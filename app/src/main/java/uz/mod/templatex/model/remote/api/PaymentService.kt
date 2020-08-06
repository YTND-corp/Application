package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.PaymentResponse

interface PaymentService {
    @FormUrlEncoded
    @POST("v1/payment")
    fun pay(
        @Field("card") cardNumber: String,
        @Field("expire") cardExpire: String,
        @Field("amount") moneyAmount: Int,
        @Field("provider") paymentProvider: String,
        @Field("phone") phoneNumber : String
    ): LiveData<ApiResponse<PaymentResponse>>
}