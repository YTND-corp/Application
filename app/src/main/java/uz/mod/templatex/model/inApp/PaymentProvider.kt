package uz.mod.templatex.model.inApp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import uz.mod.templatex.model.remote.response.PaymentMethod
import uz.mod.templatex.model.remote.response.PaymentProvider


@Parcelize
data class PaymentData(
    var provider: PaymentProvider? = null,
    var phone: String? = null,
    val amount: Int,
    val currency: String = "UZS",
    var paymentMethod: PaymentMethod? = null
) : Parcelable


