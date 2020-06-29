package uz.mod.templatex.model.remote.response

import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.local.entity.Product

data class ProductsResponse(@SerializedName("products") val productWrapper: ProductWrapper?, val filter: Filter?)

data class ProductWrapper(var data: List<Product>?, val pagination: Pagination)


data class Pagination (
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("per_page") val pageSize: Int,
    val total: Int
)