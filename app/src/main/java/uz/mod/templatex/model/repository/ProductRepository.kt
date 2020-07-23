package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.FilterDao
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.api.FavoritesService
import uz.mod.templatex.model.remote.api.ProductService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.ProductDetailResponse
import uz.mod.templatex.model.remote.response.ProductsResponse
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel

class ProductRepository constructor(
    val productService: ProductService,
    val favoritesService: FavoritesService,
    val productDao: ProductDao,
    val filterDao: FilterDao,
    val executors: AppExecutors
) {

    init {
        Timber.d("Injection CatalogRepository")
    }

    fun getFilters() = filterDao.getAll()

    fun getFiltersForCategory(catId: Int): Filter? {
        return filterDao.get(catId)
    }

    fun getProduct(id: Int): LiveData<Resource<ProductDetailResponse>> {
        return object : NetworkOnlyResource<ProductDetailResponse, ProductDetailResponse>() {
            override fun processResult(item: ProductDetailResponse?): ProductDetailResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<ProductDetailResponse>> {
                return productService.getProduct(id)
            }

        }.asLiveData()
    }


    fun getProducts(id: Int, filter: SharedFilterViewModel.SelectedFitlerDto, page: Int): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, ProductsResponse>(executors) {
            override fun saveCallResult(item: ProductsResponse) {
                if (page == 1) {
                    productDao.deleteAll()
                    filterDao.deleteAll()
                    Timber.d("Deleted products")
                }

                item.productWrapper?.data?.let {
                    productDao.insertAll(it)
                    Timber.d("Inserted ${it.size} products")
                }

                item.filter?.let {
                    it.id = id
                    it.pagination = item.productWrapper?.pagination
                    filterDao.insert(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                val all = productDao.getAll()
                return all
            }

            override fun createCall(): LiveData<ApiResponse<ProductsResponse>> {
                val attrMap = filter.attributes.mapValues { it.value as Any }.mapKeys { it.key + "[]" }.toMutableMap()
                val proxyRetrofitQueryMap = ProxyRetrofitQueryMap(attrMap)
                return productService.getProducts(
                    id, filter.sort.key, filter.brands.map { it.toString() }.toTypedArray(),
                    proxyRetrofitQueryMap,
                    page
                )
            }
        }.asLiveData()
    }

    fun favoriteToggle(id: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                productDao.setFavorite(id, !productDao.get(id).isFavorite)
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return favoritesService.toggleFavorite(id)
            }
        }.asLiveData()
    }

    fun seeAlsoFavoriteToggle(id: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return favoritesService.toggleFavorite(id)
            }
        }.asLiveData()
    }

    fun isFavorite(id: Int) = productDao.getLiveProduct(id)
}