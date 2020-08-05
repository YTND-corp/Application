package uz.mod.templatex.model.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.utils.extension.moneyFormat

data class ConfirmResponse(val confirmation: Boolean?, val user: User?, val delivery: List<Delivery>?, val payment: Payment?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readParcelable(User::class.java.classLoader),
        parcel.createTypedArrayList(Delivery),
        parcel.readParcelable(Payment::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(confirmation)
        parcel.writeParcelable(user, flags)
        parcel.writeTypedList(delivery)
        parcel.writeParcelable(payment, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConfirmResponse> {
        override fun createFromParcel(parcel: Parcel): ConfirmResponse {
            return ConfirmResponse(parcel)
        }

        override fun newArray(size: Int): Array<ConfirmResponse?> {
            return arrayOfNulls(size)
        }
    }


}

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

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(transitTime)
        parcel.writeValue(price)
        parcel.writeValue(carrierId)
        parcel.writeValue(sortOrder)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Delivery> {
        override fun createFromParcel(parcel: Parcel): Delivery {
            return Delivery(parcel)
        }

        override fun newArray(size: Int): Array<Delivery?> {
            return arrayOfNulls(size)
        }
    }

}


data class Payment(
    val methods: List<PaymentMethod>?,
    val providers: List<PaymentProvider>?,
    val cart: Cart?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(PaymentMethod),
        parcel.createTypedArrayList(PaymentProvider),
        TODO("cart")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(methods)
        parcel.writeTypedList(providers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Payment> {
        override fun createFromParcel(parcel: Parcel): Payment {
            return Payment(parcel)
        }

        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }

}

data class PaymentProvider(
    val id: String?,
    val media: String?,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(media)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentProvider> {
        override fun createFromParcel(parcel: Parcel): PaymentProvider {
            return PaymentProvider(parcel)
        }

        override fun newArray(size: Int): Array<PaymentProvider?> {
            return arrayOfNulls(size)
        }
    }

}

data class PaymentMethod(val type: String?, val name: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentMethod> {
        override fun createFromParcel(parcel: Parcel): PaymentMethod {
            return PaymentMethod(parcel)
        }

        override fun newArray(size: Int): Array<PaymentMethod?> {
            return arrayOfNulls(size)
        }
    }

}