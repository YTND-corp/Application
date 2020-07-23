package uz.mod.templatex.model.remote.api

import androidx.lifecycle.LiveData
import retrofit2.http.*
import uz.mod.templatex.model.remote.network.ApiResponse
import uz.mod.templatex.model.remote.network.ProxyRetrofitQueryMap
import uz.mod.templatex.model.remote.response.CartResponse
import uz.mod.templatex.model.remote.response.ProductDetailResponse
import uz.mod.templatex.model.remote.response.ProductsResponse

interface ProductService {

    @GET("v1/categories/{id}/products")
    fun getProducts(
        @Path("id") id: Int,
        @Query("sort") sort: String? = null,
        @Query("brands[]") brands: Array<String>? = null,
        @QueryMap attrMap: @JvmSuppressWildcards ProxyRetrofitQueryMap,
        @Query("page") page: Int = 1
    ): LiveData<ApiResponse<ProductsResponse>>

    @GET("v1/products/{id}/details")
    fun getProduct(
        @Path("id") id: Int
    ): LiveData<ApiResponse<ProductDetailResponse>>

    @FormUrlEncoded
    @POST("v1/carts")
    fun addToCart(
        @Field("product") id: Int,
        @Field("quantity") quantity: Int,
        @Field("attributes[]") attributes: ArrayList<Int>
    ): LiveData<ApiResponse<CartResponse>>

}