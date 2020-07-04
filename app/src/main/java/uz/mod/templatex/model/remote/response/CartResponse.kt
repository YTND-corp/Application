package uz.mod.templatex.model.remote.response

import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.*

data class CartResponse(val cart: Cart, val recommended: List<Product>)

data class Cart(
    val id: Int,
    val uid: String?,
    val carrier: String?,
    @SerializedName("products_price") val productsPrice: Int,
    @SerializedName("delivery_price") val deliveryPrice: Int,
    @SerializedName("discount_price") val discountPrice: Int,
    @SerializedName("total_price") val totalPrice: Int,
    val quantity: Int,
    val products: List<Product>?
)

data class CartProduct(
    val id: Int,
    @SerializedName("cart_product_id") val cartProductId: Int,
    val name: String,
    val reference: String,
    val brand: String,
    val category: String,
    @SerializedName("is_favorites_added") val isFavoritesAdded: Boolean,
    @SerializedName("currency") val currencies: List<Currency>,
    val image: String?,
    var selected: Boolean,
    var quantity: Int
) {


}