package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.extension.moneyFormat
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.api.ProductService
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.responce.*

class ProductRepository constructor(val service: ProductService, val db: ProductDao) {

    init {
        Timber.d("Injection CatalogRepository")
    }

    fun getProducts() = db.getAll()


    fun getProduct(categoryId: Int, id: Int): LiveData<Resource<ProductDetailResponse>> {
        return object : NetworkOnlyResource<ProductDetailResponse, ProductDetailResponse>() {
            override fun processResult(item: ProductDetailResponse?): ProductDetailResponse? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<ProductDetailResponse>> {
                return service.getProduct(categoryId, id)
            }

        }.asLiveData()
    }

    fun getProducts(id: Int, sort: String, brands: Array<String>?, page: Int): LiveData<Resource<Int>> {
        return object : NetworkOnlyResource<Int, ProductsResponse>() {
            override fun processResult(item: ProductsResponse?): Int? {
                if (page == 1) db.deleteAll()

                item?.productWrapper?.data?.let { items ->
                    val temps = ArrayList<Product>()
                    items.forEach {
                        temps.add(
                            Product(
                                it.id,
                                it.name,
                                it.price.moneyFormat() + " " + it.currencies?.first()?.currency,
                                it.isFavorite,
                                it.image,
                                it.brand?.name,
                                it.category?.id ?: 0
                            )
                        )
                    }
                    db.insertAll(temps)
                }
                return item?.productWrapper?.total
            }

            override fun createCall(): LiveData<ApiResponse<ProductsResponse>> {
                return service.getProducts(id, null, brands, page)
            }

        }.asLiveData()
    }

    fun getFavorites(): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, List<Favorite>>() {
            override fun processResult(item: List<Favorite>?): Any? {
                db.deleteAll()
                item?.let { items ->
                    val temps = ArrayList<Product>()
                    items.forEach {
                        temps.add(
                            Product(
                                it.id,
                                it.name,
                                it.price.moneyFormat() + " " + it.currencies?.first()?.currency,
                                it.isFavorite,
                                it.image,
                                it.brand?.name,
                                it.category?.id ?: 0
                            )
                        )
                    }
                    db.insertAll(temps)
                }

                return item
            }

            override fun createCall(): LiveData<ApiResponse<List<Favorite>>> {
                return service.getFavorites()
            }
        }.asLiveData()
    }

    fun favoriteToggle(id: Int, isFavorite: Boolean): LiveData<Resource<String>> {
        return object : NetworkOnlyResource<String, FavoriteToggleResponse>() {
            override fun processResult(item: FavoriteToggleResponse?): String? {
                db.setFavorite(id, isFavorite)
                return item?.result
            }

            override fun createCall(): LiveData<ApiResponse<FavoriteToggleResponse>> {
                return service.favoriteToggle(id)
            }
        }.asLiveData()
    }

    fun addToCart(id: Int, quantity: Int, attributes: ArrayList<String>?): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.addToCart(id,quantity, attributes)
            }

        }.asLiveData()
    }

//    fun loadMore(id: Int): LiveData<Resource<List<Product>>> {
//        return object : NetworkOnlyResource<List<Product>, List<Product>>() {
//            override fun processResult(item: List<Product>?): List<Product>? {
//                return  item
//            }
//
//            override fun createCall(): LiveData<ApiResponse<List<Product>>> {
//                return service.getProducts(id,"popular")
//            }
//
//        }.asLiveData()
//    }
}