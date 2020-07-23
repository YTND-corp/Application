package uz.mod.templatex.model.remote.response

import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.Product

data class CartResponse(val cart: Cart, val recommended: List<Product>)

data class Cart(
    val id: Int,
    val uid: String?,
    val carrier: List<Carrier>?,
    @SerializedName("products_price") val productsPrice: Int,
    @SerializedName("delivery_price") val deliveryPrice: Int,
    @SerializedName("discount_price") val discountPrice: Int,
    @SerializedName("total_price") val totalPrice: Int,
    val quantity: Int,
    val products: List<Product>?
)

data class Carrier(
    val id: Int,
    val name: String
)