package uz.mod.templatex.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.utils.extension.moneyFormat

@Parcelize
data class ConfirmResponse(
    val confirmation: Boolean?,
    val user: User?,
    val delivery: List<Delivery>?,
    val payment: Payment?
) : Parcelable

@Parcelize
data class Delivery(
    val id: Int,
    val name: String?,
    val description: String?,
    @SerializedName("transit_time") val transitTime: String?,
    val price: Int?,
    @SerializedName("carrier_id") val carrierId: Int?,
    @SerializedName("sort_order") val sortOrder: Int?,
    val date: String?
) : Parcelable {
    fun formattedPrice() = if (price == 0) "БЕСПЛАТНО" else "${price?.moneyFormat()} UZS"
}

@Parcelize
data class Payment(
    val methods: List<PaymentMethod>?,
    val providers: List<PaymentProvider>?,
    val cart: Cart?
) : Parcelable

@Parcelize
data class PaymentProvider(
    val id: String?,
    val media: String?,
    val name: String?
) : Parcelable

@Parcelize
data class PaymentMethod(val type: String?, val name: String?) : Parcelable