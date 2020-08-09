package uz.mod.templatex.model.repository.profile

import androidx.lifecycle.LiveData
import uz.mod.templatex.model.remote.api.profile.SupportCenterService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.profile.Page
import uz.mod.templatex.model.remote.response.profile.SupportCenterPagesResponse

class SupportCenterRepository(val service: SupportCenterService) {

    fun getPages(): LiveData<Resource<List<Page>>> {
        return object : NetworkOnlyResource<List<Page>, SupportCenterPagesResponse>() {
            override fun processResult(item: SupportCenterPagesResponse?): List<Page>? {
                return item?.pages
            }

            override fun createCall(): LiveData<ApiResponse<SupportCenterPagesResponse>> {
                return service.getPages()
            }
        }.asLiveData()
    }
}