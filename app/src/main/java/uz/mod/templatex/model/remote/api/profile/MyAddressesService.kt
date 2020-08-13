package uz.mod.templatex.model.remote.api.profile

import androidx.lifecycle.LiveData
import retrofit2.http.*
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.profile.MyAddressesCreateInfoResponse
import uz.mod.templatex.model.remote.response.profile.MyAddressesResponse

interface MyAddressesService {
    @GET("v1/profile/addresses")
    fun getAddresses(): LiveData<ApiResponse<MyAddressesResponse>>

    @GET("v1/profile/addresses/create")
    fun getCreateInfo(): LiveData<ApiResponse<MyAddressesCreateInfoResponse>>

    @FormUrlEncoded
    @POST("v1/profile/addresses")
    fun store(
        @Field("first_name") firstName: String?,
        @Field("last_name") lastName: String?,
        @Field("phone") phone: String?,
        @Field("city") city: String?,
        @Field("street") street: String?,
        @Field("building") building: String?,
        @Field("flat") flat: String?,
        @Field("entry") entry: String?,
        @Field("postcode") postcode: String?,
        @Field("region_id") regionId: Int?,
        @Field("is_default") isDefault: Int?
    ): LiveData<ApiResponse<Any>>

    @GET("v1/profile/addresses/{id}/edit")
    fun edit(@Path("id") id: Int): LiveData<ApiResponse<Any>>

    @FormUrlEncoded
    @PATCH("v1/profile/addresses/{id}")
    fun update(
        @Path("id") id: Int,
        @Field("first_name") firstName: String?,
        @Field("last_name") lastName: String?,
        @Field("phone") phone: String?,
        @Field("city") city: String?,
        @Field("street") street: String?,
        @Field("building") building: String?,
        @Field("flat") flat: String?,
        @Field("entry") entry: String?,
        @Field("postcode") postcode: String?,
        @Field("region_id") regionId: Int?,
        @Field("is_default") isDefault: Int? = 0
    ): LiveData<ApiResponse<Any>>

    @DELETE("v1/profile/addresses/{id}")
    fun delete()
}