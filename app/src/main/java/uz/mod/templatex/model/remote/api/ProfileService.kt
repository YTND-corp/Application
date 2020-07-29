package uz.mod.templatex.model.remote.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProfileService {

    @FormUrlEncoded
    @POST("v1/profile/callback")
    fun callback(@Field("phone") phone: String)

    @FormUrlEncoded
    @POST("v1/profile/orders/check")
    fun checkOrderState(@Field("reference") reference: String)
}