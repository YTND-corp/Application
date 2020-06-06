package uz.uzmobile.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import uz.uzmobile.templatex.model.local.entity.CategoryGender
import uz.uzmobile.templatex.model.local.entity.HomeGender
import uz.uzmobile.templatex.model.remote.network.ApiResponse

interface CategoryService {

    @GET("v1")
    fun getHome(): LiveData<ApiResponse<List<HomeGender>>>

    @GET("v1/catalogs")
    fun getCategories(): LiveData<ApiResponse<List<CategoryGender>>>

}