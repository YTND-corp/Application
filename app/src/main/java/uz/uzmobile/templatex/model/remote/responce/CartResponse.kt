package uz.uzmobile.templatex.model.remote.responce

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import uz.uzmobile.templatex.extension.moneyFormat
import uz.uzmobile.templatex.model.local.entity.*
import uz.uzmobile.templatex.model.local.entity.Product

data class CartResponse(val cart: Cart)

data class Cart(
    val id: Int,
    val uid: String?,
    @SerializedName("is_finished") val isFinished: Boolean,
    @SerializedName("user") val user: User?,
    val carrier: Carrier?,
    val productsPrice: Int,
    val productsPriceFormatted: String?,
    val deliveryPrice: Int,
    val deliveryPriceFormatted: String?,
    val discountPrice: Int,
    val discountPriceFormatted: String?,
    val totalPrice: Int,
    val totalPriceFormatted: String?,
    val quantity: Int,
    @SerializedName("cart_products") val products: List<CartProductWrapper>?
)

data class CartProductWrapper(
    val id: Int,
    var selected: Boolean,
    var quantity: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("product") val product: CartProduct?
) {

    fun quantityText() = "$quantity"
    fun subtitle() = "${product?.brand?.name} - ${product?.category?.name}"
    fun totalPrice() = quantity * (product?.price ?: 0)
    fun totalPriceFormatted() = totalPrice().moneyFormat() + " UZS"
}

data class CartProduct(
    val id: Int,
    val name: String?,
    val image: String?,
    val price: Int,
    @SerializedName("old_price") @Expose val oldPrice: Int,
    @SerializedName("category_id") @Expose val categoryId: Int,
    val discount: Int,
    val quantity: Int,
    val brand: Brand?,
    val category: Category?
) {
    fun priceFormatted() = price.moneyFormat() + " UZS"
}

data class Carrier(
    val id: Int,
    val name: String?,
    val description: String?,
    @SerializedName("transit_time") val transitTime: String?,
    val price: Int,
    @SerializedName("carrier_id") val carrierId: Int,
    @SerializedName("sort_order") val sortOrder: Int
)