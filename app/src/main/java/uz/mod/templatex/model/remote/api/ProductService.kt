package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.*
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.response.*

interface ProductService {

    @GET("v1/categories/{id}/products")
    fun getProducts(
        @Path("id") id: Int,
        @Query("sort") sort: String? = null,
        @Query("brands[]") brands: Array<String>? = null,
        @Query("page") page: Int = 1
    ): LiveData<ApiResponse<ProductsResponse>>

    @GET("v1/products/{id}/details")
    fun getProduct(
        @Path("id") id: Int
    ): LiveData<ApiResponse<ProductDetailResponse>>

    @GET("v1/favorites")
    fun getFavorites(): LiveData<ApiResponse<FavoritesResponse>>

    @FormUrlEncoded
    @POST("v1/favorites")
    fun favoriteToggle(@Field("product") id: Int): LiveData<ApiResponse<FavoriteToggleResponse>>

    @FormUrlEncoded
    @POST("v1/carts/products/add")
    fun addToCart(@Field("product") id: Int, @Field("quantity") quantity: Int, @Field("attributes[]") attributes: ArrayList<Int>): LiveData<ApiResponse<Any>>

}