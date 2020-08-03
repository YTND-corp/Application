package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.search.SearchResponse

interface SearchService {
    @GET("v1/search")
    fun search(@Query("q") query: String): LiveData<ApiResponse<SearchResponse>>
}