package uz.mod.templatex.model.remote.api.profile

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.profile.MyOrdersOrderDetailsResponse
import uz.mod.templatex.model.remote.response.profile.MyOrdersResponse

interface MyOrdersService {
    @GET("v1/profile/orders")
    fun getOrders(@Query("q") searchText: String): LiveData<ApiResponse<MyOrdersResponse>>

    @GET("v1/profile/orders/{id}")
    fun getOrderDetails(@Path("id") id: Int): LiveData<ApiResponse<MyOrdersOrderDetailsResponse>>
}