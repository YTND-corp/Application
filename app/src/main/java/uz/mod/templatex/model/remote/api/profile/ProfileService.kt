package uz.mod.templatex.model.remote.api.profile

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.profile.CheckOrderStateResponse

interface ProfileService {

    @FormUrlEncoded
    @POST("v1/profile/callback")
    fun callback(@Field("phone") phone: String): LiveData<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("v1/profile/orders/check")
    fun checkOrderState(@Field("reference") reference: String): LiveData<ApiResponse<CheckOrderStateResponse>>

    @FormUrlEncoded
    @POST("v1/products/1/questions/add")
    fun askQuestion(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("question") question: String
    ): LiveData<ApiResponse<Any>>
}