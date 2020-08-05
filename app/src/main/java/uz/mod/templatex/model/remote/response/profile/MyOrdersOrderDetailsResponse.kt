package uz.mod.templatex.model.remote.response.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MyOrdersOrderDetailsResponse(
    val order: Order,
    val cart: Cart,
    val address: Address
)

data class Order(
    val id: Int,
    val reference: String,
    val quantity: Int,
    @SerializedName("total_price")
    val totalPrice: Double,
    val state: State?
) {
    fun printOrderId() = "№$reference"
}

data class State(
    val type: StateType,
    val name: String,
    val time: String
) {
    fun printStateWithDate() = "$name - $time"
}

enum class StateType(type: String) {
    INFO("info"),
    WARNING("warning"),
    SUCCESS("success"),
    DANGER("danger")
}

data class Cart(
    val id: Int,
    val uid: String,
    val carrier: Carrier,
    @SerializedName("products_price")
    val productsPrice: Double,
    @SerializedName("delivery_price")
    val deliveryPrice: Double,
    @SerializedName("discount_price")
    val discountPrice: Double,
    @SerializedName("total_price")
    val totalPrice: Double,
    val quantity: Int,
    val products: List<Product>
) {
    fun printProductsPrice() = "$productsPrice сум"

    fun printDeliveryPrice() = "$deliveryPrice сум"

    fun printTotalPrice() = "$totalPrice сум"
}

data class Product(
    @SerializedName("cart_product_id")
    val cartProductId: Int,
    val id: Int,
    val name: String,
    val reference: String,
    val brand: String,
    val currency: List<Currency>,
    val quantity: Int,
    val category: String,
    @SerializedName("is_favorites_added")
    val isFavoriteAdded: Boolean,
    val image: String,
    @SerializedName("attribute_combination")
    val attributeCombination: List<AttributeCombination>
)

data class Carrier(
    val id: Int,
    val name: String
)

data class Currency(
    @SerializedName("currency")
    val currency: String,
    val price: Int,
    @SerializedName("old_price")
    val oldPrice: Int,
    val discount: Int
) {
    fun printPrice() = "$price $currency"
}

data class AttributeCombination(
    val key: String,
    val value: String
)

@Parcelize
data class Address(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val phone: String?,
    val region: String?,
    val city: String?,
    val street: String?,
    val building: String?,
    val entry: String?,
    val postcode: String?,
    @SerializedName("is_default")
    val isDefault: Boolean?
) : Parcelable {
    fun getAddress() = arrayOf(street, building, entry, city, region).map { it?.trim() }.filter { !it.isNullOrEmpty() }.joinToString()
}