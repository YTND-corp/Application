package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import uz.mod.templatex.model.remote.api.SearchService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.search.SearchResponse

class SearchRepository(val service: SearchService) {
    fun search(query: String): LiveData<Resource<SearchResponse>> {
        return object : NetworkOnlyResource<SearchResponse, SearchResponse>() {
            override fun processResult(item: SearchResponse?): SearchResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<SearchResponse>> {
                return service.search(query)
            }
        }.asLiveData()
    }
}