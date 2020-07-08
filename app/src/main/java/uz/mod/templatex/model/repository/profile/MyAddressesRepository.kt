package uz.mod.templatex.model.repository.profile

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.ProfileAddressDao
import uz.mod.templatex.model.local.db.dao.ProfileRegionDao
import uz.mod.templatex.model.local.entity.profile.ProfileAddress
import uz.mod.templatex.model.local.entity.profile.ProfileRegion
import uz.mod.templatex.model.remote.api.profile.MyAddressesService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.profile.MyAddressesCreateInfoResponse
import uz.mod.templatex.model.remote.response.profile.MyAddressesResponse

class MyAddressesRepository constructor(
    val service: MyAddressesService,
    val addressesDao: ProfileAddressDao,
    val regionsDao: ProfileRegionDao,
    val executors: AppExecutors
) {
    init {
        Timber.d("Injection MyAddressesRepository")
    }

    fun getAddress(id: Int) = addressesDao.get(id)

    fun getAddresses(): LiveData<Resource<List<ProfileAddress>>> {
        return object : NetworkBoundResource<List<ProfileAddress>, MyAddressesResponse>(executors) {
            override fun saveCallResult(item: MyAddressesResponse) {
                addressesDao.deleteAll()
                addressesDao.insertAll(item.addresses)
            }

            override fun shouldFetch(data: List<ProfileAddress>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<ProfileAddress>> {
                return addressesDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<MyAddressesResponse>> {
                return service.getAddresses()
            }
        }.asLiveData()
    }

    fun getCreateInfo(): LiveData<Resource<List<ProfileRegion>>> {
        return object :
            NetworkBoundResource<List<ProfileRegion>, MyAddressesCreateInfoResponse>(executors) {
            override fun saveCallResult(item: MyAddressesCreateInfoResponse) {
                regionsDao.deleteAll()
                regionsDao.insertAll(item.regions)
            }

            override fun shouldFetch(data: List<ProfileRegion>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<ProfileRegion>> {
                return regionsDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<MyAddressesCreateInfoResponse>> {
                return service.getCreateInfo()
            }
        }.asLiveData()
    }

    fun canEditAddress(id: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.edit(id)
            }
        }.asLiveData()
    }

    fun updateAddress(
        id: Int,
        firstName: String?,
        lastName: String?,
        phone: String?,
        city: String?,
        street: String?,
        building: String?,
        flat: String?,
        entry: String?,
        postcode: String?,
        regionId: Int?,
        isDefault: Boolean?
    ): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.update(
                    id,
                    firstName,
                    lastName,
                    phone,
                    city,
                    street,
                    building,
                    flat,
                    entry,
                    postcode,
                    regionId,
                    isDefault
                )
            }
        }.asLiveData()
    }

    fun storeAddress(
        firstName: String?,
        lastName: String?,
        phone: String?,
        city: String?,
        street: String?,
        building: String?,
        flat: String?,
        entry: String?,
        postcode: String?,
        regionId: Int?,
        isDefault: Boolean?
    ): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.store(
                    firstName,
                    lastName,
                    phone,
                    city,
                    street,
                    building,
                    flat,
                    entry,
                    postcode,
                    regionId,
                    isDefault
                )
            }
        }.asLiveData()
    }
}