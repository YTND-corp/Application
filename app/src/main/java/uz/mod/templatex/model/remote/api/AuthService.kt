package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.AuthConfirmationResponse

interface AuthService {

    @FormUrlEncoded
    @POST("v1/auth/register")
    fun signUp(
        @Field("first_name") name: String,
        @Field("last_name") surname: String?,
        @Field("email") email: String?,
        @Field("phone") phone: String
    ): LiveData<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("v1/auth/login")
    fun signIn(@Field("phone") phone: String): LiveData<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("v1/auth/confirm")
    fun confirm(@Field("code") code: String): LiveData<ApiResponse<AuthConfirmationResponse>>
}