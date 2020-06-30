package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import uz.mod.templatex.model.local.entity.CategoryGender
import uz.mod.templatex.model.local.entity.HomeGender
import uz.mod.templatex.model.remote.network.ApiResponse

interface CategoryService {

    @GET("v1")
    fun getHome(): LiveData<ApiResponse<List<HomeGender>>>

    @GET("v1/catalogs")
    fun getCategories(): LiveData<ApiResponse<List<CategoryGender>>>

    @GET("v1/catalogs/{id}")
    fun getCategoryWithFilter(@Path("id")catId : String) : LiveData<ApiResponse<Any>>

}