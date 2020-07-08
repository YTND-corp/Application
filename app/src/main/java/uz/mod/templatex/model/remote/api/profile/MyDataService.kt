package uz.mod.templatex.model.remote.api.profile

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.profile.MyDataResponse

interface MyDataService {
    @GET("v1/profile/info")
    fun getUserInfo(): LiveData<ApiResponse<MyDataResponse>>

    @FormUrlEncoded
    @PATCH("v1/profile/info/update")
    fun updateUserInfo(
        @Field("first_name")
        firstName: String?,
        @Field("last_name")
        lastName: String?,
        @Field("phone")
        phone: String?,
        @Field("email")
        email: String?,
        @Field("birthday")
        birthday: String?,
        @Field("gender")
        gender: String?,
        @Field("notifications")
        notifications: Int?,
        @Field("subscriptions")
        subscriptions: Int?
    ): LiveData<ApiResponse<Any>>
}