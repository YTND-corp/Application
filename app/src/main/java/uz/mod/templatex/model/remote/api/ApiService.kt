package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.*
import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.local.entity.News
import uz.mod.templatex.model.remote.network.ApiResponse

interface ApiService {

    @POST("b/ap/stream/ph&get_news")
    fun news(): LiveData<ApiResponse<List<News>>>

    @POST("b/ap/stream/ph&filials")
    fun brands(): LiveData<ApiResponse<List<Brand>>>
//
//    @POST("b/ap/stream/ph&models")
//    fun models(@Body brand: Brand): LiveData<ApiResponse<List<Model>>>
}