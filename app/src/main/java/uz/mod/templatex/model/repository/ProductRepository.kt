package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.FilterDao
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.api.ProductService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.*

class ProductRepository constructor(
    val service: ProductService,
    val productDao: ProductDao,
    val filterDao: FilterDao,
    val executors: AppExecutors
) {

    init {
        Timber.d("Injection CatalogRepository")
    }

    fun getFilters() = filterDao.getAll()

    fun getProduct(id: Int): LiveData<Resource<ProductDetailResponse>> {
        return object : NetworkOnlyResource<ProductDetailResponse, ProductDetailResponse>() {
            override fun processResult(item: ProductDetailResponse?): ProductDetailResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<ProductDetailResponse>> {
                return service.getProduct(id)
            }

        }.asLiveData()
    }

//    fun getProducts(
//        id: Int,
//        sort: String,
//        brands: Array<String>?,
//        page: Int
//    ): LiveData<Resource<Int>> {
//        return object : NetworkOnlyResource<Int, ProductsResponse>() {
//            override fun processResult(item: ProductsResponse?): Int? {
//                if (page == 1)
//                    db.deleteAll()
//                var temps = ArrayList<Product>()
//                item?.productWrapper?.data?.forEach {
//                    temps.add(Product(it.id, it.name, it.currency[0].getMoneyFormat(), it.isFavorite, it.image, it.brand, it.categoryId))
//                }
//
//                db.insertAll(temps)
//                return item?.productWrapper?.total
//            }
//
//            override fun createCall(): LiveData<ApiResponse<ProductsResponse>> {
//                return service.getProducts(id, null, brands, page)
//            }
//        }.asLiveData()
//    }

    fun getProducts(id: Int, sort: String, brands: Array<String>?, page: Int): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, ProductsResponse>(executors) {
            override fun saveCallResult(item: ProductsResponse) {
                if (page == 1) {
                    productDao.deleteAll()
                    filterDao.deleteAll()
                }

                item.productWrapper?.data?.let {
                    productDao.insertAll(it)
                }

                item.filter?.let {
                    it.pagination = item.productWrapper?.pagination
                    filterDao.insert(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                return productDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<ProductsResponse>> {
                return service.getProducts(id, null, brands, page)
            }
        }.asLiveData()
    }

    fun favorites(): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, FavoritesResponse>(executors) {

            override fun saveCallResult(item: FavoritesResponse) {
                productDao.deleteAll()
                item.data?.let {
                    productDao.insertAll(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                return productDao.getFavorites()
            }

            override fun createCall(): LiveData<ApiResponse<FavoritesResponse>> {
                return service.getFavorites()
            }
        }.asLiveData()
    }

    fun favoriteToggle(id: Int): LiveData<Resource<String>> {
        return object : NetworkOnlyResource<String, FavoriteToggleResponse>() {
            override fun processResult(item: FavoriteToggleResponse?): String? {
                productDao.setFavorite(id, !productDao.get(id).isFavorite)
                return item?.result
            }

            override fun createCall(): LiveData<ApiResponse<FavoriteToggleResponse>> {
                return service.favoriteToggle(id)
            }
        }.asLiveData()
    }

    fun addToCart(
        id: Int,
        quantity: Int,
        attributes: ArrayList<Int>
    ): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.addToCart(id, quantity, attributes)
            }

        }.asLiveData()
    }

    fun isFavorite(id: Int) = productDao.getLiveProduct(id)
}