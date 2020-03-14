package uz.uzmobile.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.uzmobile.templatex.model.local.entity.Brand
import uz.uzmobile.templatex.model.remote.api.ApiService
import uz.uzmobile.templatex.model.remote.network.ApiResponse
import uz.uzmobile.templatex.model.remote.network.NetworkOnlyResource
import uz.uzmobile.templatex.model.remote.network.Resource

class ApiRepository constructor(val service: ApiService) {

    init {
        Timber.d("Injection CarRepository")
    }

    fun getBrands(): LiveData<Resource<List<Brand>>> {
        return object : NetworkOnlyResource<List<Brand>, List<Brand>>() {
            override fun processResult(item: List<Brand>?): List<Brand>? {
                return  item
            }

            override fun createCall(): LiveData<ApiResponse<List<Brand>>> {
                return service.brands()
            }

        }.asLiveData()
    }


}