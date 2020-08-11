package uz.mod.templatex.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import uz.mod.templatex.model.local.entity.Product

data class CartResponse(val cart: Cart, val recommended: List<Product>)

@Parcelize
data class Cart(
    val id: Int,
    val uid: String?,
    val carrier: Carrier,
    @SerializedName("products_price") val productsPrice: Int,
    @SerializedName("delivery_price") val deliveryPrice: Int,
    @SerializedName("discount_price") val discountPrice: Int,
    @SerializedName("total_price") val totalPrice: Int,
    val quantity: Int,
    val products: List<Product>?
) : Parcelable

@Parcelize
data class Carrier(
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("sort_order")
    val sortOrder: Int
) : Parcelable