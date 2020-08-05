package uz.mod.templatex.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.response.profile.Address

@Parcelize
data class StoreResponse(
    val user: User?,
    val order: Order,
    val date: String?,
    val cart: Cart,
    val delivery: StoreDelivery,
    val address: Address,
    val notifications: Boolean
) : Parcelable

@Parcelize
data class Order(
    val reference: String,
    val user_id: Long,
    val cart_id: Long,
    val address_id: Long,
    val delivery_id: Long,
    val payment_type: String,
    val payment_provider: String,
    val products_price: Long,
    val delivery_price: Long,
    val discount_price: Int,
    val total_price: Int,
    val updated_at: String,
    val created_at: String,
    val id: Long
) : Parcelable


@Parcelize
data class StoreDelivery(
    @SerializedName("carrier_id")
    val carrierID: Long,
    @SerializedName("carrier_service_id")
    val carrierServiceID: String,
    val date: StoreDate,
    val time: String,
    val updatedAt: String,
    val createdAt: String,
    val id: Long
) : Parcelable

@Parcelize
data class StoreDate(
    val date: String,
    @SerializedName("timezone_type")
    val timezoneType: String,
    val timezone: String
) : Parcelable