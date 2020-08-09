package uz.mod.templatex.model.remote.api.profile

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.profile.SupportCenterPagesResponse

interface SupportCenterService {
    @GET("v1/pages")
    fun getPages(): LiveData<ApiResponse<SupportCenterPagesResponse>>
}