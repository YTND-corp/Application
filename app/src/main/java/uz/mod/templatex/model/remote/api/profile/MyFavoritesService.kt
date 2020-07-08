package uz.mod.templatex.model.remote.api.profile

import androidx.lifecycle.LiveData
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.profile.MyFavoritesResponse

interface MyFavoritesService {
    @GET("v1/favorites")
    fun getFavorites(): LiveData<ApiResponse<MyFavoritesResponse>>

    @Multipart
    @POST("v1/favorites")
    fun toggleFavorite(@Part("product") id: RequestBody): LiveData<ApiResponse<Any>>
}