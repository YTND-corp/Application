package uz.mod.templatex.model.repository.profile

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.ProfileOrderDao
import uz.mod.templatex.model.local.entity.profile.ProfileOrder
import uz.mod.templatex.model.remote.api.profile.MyOrdersService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.profile.MyOrdersOrderDetailsResponse
import uz.mod.templatex.model.remote.response.profile.MyOrdersResponse

class MyOrdersRepository(
    val service: MyOrdersService,
    val ordersDao: ProfileOrderDao,
    val executors: AppExecutors
) {
    init {
        Timber.d("Injection MyOrdersRepository")
    }

    fun getOrders(searchText: String): LiveData<Resource<List<ProfileOrder>>> {
        return object : NetworkBoundResource<List<ProfileOrder>, MyOrdersResponse>(executors) {
            override fun saveCallResult(item: MyOrdersResponse) {
                ordersDao.deleteAll()
                ordersDao.insertAll(item.orders)
            }

            override fun shouldFetch(data: List<ProfileOrder>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<ProfileOrder>> {
                return ordersDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<MyOrdersResponse>> {
                return service.getOrders(searchText)
            }
        }.asLiveData()
    }

    fun getOrder(id: Int): LiveData<Resource<MyOrdersOrderDetailsResponse>> {
        return object :
            NetworkOnlyResource<MyOrdersOrderDetailsResponse, MyOrdersOrderDetailsResponse>() {
            override fun processResult(item: MyOrdersOrderDetailsResponse?): MyOrdersOrderDetailsResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<MyOrdersOrderDetailsResponse>> {
                return service.getOrderDetails(id)
            }
        }.asLiveData()
    }
}