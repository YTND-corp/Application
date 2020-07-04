package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.api.CartService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.CartResponse
import uz.mod.templatex.model.remote.response.FavoritesResponse
import uz.mod.templatex.utils.AbsentLiveData

class CartRepository constructor(val service: CartService, val productDao: ProductDao,val executors: AppExecutors) {

    init {
        Timber.d("Injection CartRepository")
    }

    fun products(): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, CartResponse>(executors) {

            override fun saveCallResult(item: CartResponse) {
                productDao.deleteAll()
                item.cart.products?.let {
                    productDao.insertAll(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                return productDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<CartResponse>> {
                return service.getCart()
            }
        }.asLiveData()
    }

    fun remove(id: Int): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, CartResponse>(executors) {

            override fun saveCallResult(item: CartResponse) {
                productDao.deleteAll()
                item.cart.products?.let {
                    productDao.insertAll(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                return productDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<CartResponse>> {
                return service.remove(id)
            }
        }.asLiveData()
    }

//    fun updateProductCount(id: Int, count: Int): LiveData<Resource<Void>> {
//        return object : NetworkBoundResource<Void, Void>(executors) {
//
//            override fun saveCallResult(item: Void) {
//                Timber.e("Saving to database")
//                productDao.updateCount(id, count)
//            }
//
//            override fun shouldFetch(data: Void?): Boolean {
//                return true
//            }
//
//            override fun loadFromDb(): LiveData<Void> {
//                return AbsentLiveData.create()
//            }
//
//            override fun createCall(): LiveData<ApiResponse<Void>> {
//                Timber.e("update...${id} + ${count}")
//                return service.updateCount(id,count)
//            }
//        }.asLiveData()
//    }

    fun updateProductCount(id: Int, count: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                productDao.updateCount(id, count)
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.updateCount(id, count)
            }

        }.asLiveData()
    }

    fun select(id: Int, isSelected: Boolean) {
        productDao.setSelect(id, isSelected)
    }
}