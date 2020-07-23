package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import okhttp3.RequestBody
import retrofit2.http.*
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.FavoritesResponse

interface FavoritesService {
    @GET("v1/favorites")
    fun getFavorites(): LiveData<ApiResponse<FavoritesResponse>>

    @FormUrlEncoded
    @POST("v1/favorites")
    fun toggleFavorite(@Field("product") id: Int): LiveData<ApiResponse<Any>>
}