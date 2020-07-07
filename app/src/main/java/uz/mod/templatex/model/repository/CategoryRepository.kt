package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.entity.CategoryGender
import uz.mod.templatex.model.local.entity.HomeGender
import uz.mod.templatex.model.remote.api.CategoryService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource

class CategoryRepository constructor(val service: CategoryService) {

    init {
        Timber.d("Injection CatalogRepository")
    }

    fun getHome(): LiveData<Resource<List<HomeGender>>> {
        return object : NetworkOnlyResource<List<HomeGender>, List<HomeGender>>() {
            override fun processResult(item: List<HomeGender>?): List<HomeGender>? {
                return  item
            }

            override fun createCall(): LiveData<ApiResponse<List<HomeGender>>> {
                return service.getHome()
            }

        }.asLiveData()
    }

    fun getCategories(): LiveData<Resource<List<CategoryGender>>> {
        return object : NetworkOnlyResource<List<CategoryGender>, List<CategoryGender>>() {
            override fun processResult(item: List<CategoryGender>?): List<CategoryGender>? {
                return  item
            }

            override fun createCall(): LiveData<ApiResponse<List<CategoryGender>>> {
                return service.getCategories()
            }

        }.asLiveData()
    }

    fun getSubCategories(): LiveData<Resource<List<CategoryGender>>> {
        return object : NetworkOnlyResource<List<CategoryGender>, List<CategoryGender>>() {
            override fun processResult(item: List<CategoryGender>?): List<CategoryGender>? {
                return  item
            }

            override fun createCall(): LiveData<ApiResponse<List<CategoryGender>>> {
                return service.getCategories()
            }

        }.asLiveData()
    }
}