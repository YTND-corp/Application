package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.api.CartService
import uz.mod.templatex.model.remote.api.ProductService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.CartResponse

class CartRepository constructor(val productService: ProductService, val service: CartService, val productDao: ProductDao,val executors: AppExecutors) {

    init {
        Timber.d("Injection CartRepository")
    }

    fun products(): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, CartResponse>(executors) {

            override fun saveCallResult(item: CartResponse) {
                productDao.deleteAll()
                item.cart?.products?.let {
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

    fun delete(id: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                productDao.deleteByCartProductId(id)
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.delete(id)
            }

        }.asLiveData()
    }

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

    fun addToCart(
        id: Int,
        quantity: Int,
        attributes: ArrayList<Int>
    ): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, CartResponse>() {
            override fun processResult(item: CartResponse?): Any? {
                productDao.deleteAll()
                item?.cart?.products?.let {
                    productDao.insertAll(it)
                }
                return item
            }

            override fun createCall(): LiveData<ApiResponse<CartResponse>> {
                return productService.addToCart(id, quantity, attributes)
            }
        }.asLiveData()
    }
}