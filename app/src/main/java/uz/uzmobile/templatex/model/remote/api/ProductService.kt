package uz.uzmobile.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.*
import uz.uzmobile.templatex.model.remote.network.ApiResponse
import uz.uzmobile.templatex.model.remote.responce.*

interface ProductService {

    @GET("v1/categories/{id}/products")
    fun getProducts(
        @Path("id") id: Int,
        @Query("sort") sort: String? = null,
        @Query("brands[]") brands: Array<String>? = null,
        @Query("page") page: Int = 1
    ): LiveData<ApiResponse<ProductsResponse>>

    @GET("v1/categories/{categoryId}/products/{id}")
    fun getProduct(
        @Path("categoryId") categoryId: Int,
        @Path("id") id: Int
    ): LiveData<ApiResponse<ProductDetailResponse>>


    @GET("v1/favorites")
    fun getFavorites(): LiveData<ApiResponse<List<Favorite>>>

    @FormUrlEncoded
    @POST("v1/favorites")
    fun favoriteToggle(@Field("product") id: Int): LiveData<ApiResponse<FavoriteToggleResponse>>

    @FormUrlEncoded
    @POST("v1/carts/products/add")
    fun addToCart(@Field("product") id: Int, @Field("quantity") quantity: Int, @Field("attributes[]") attributes: ArrayList<String>?): LiveData<ApiResponse<Any>>

}