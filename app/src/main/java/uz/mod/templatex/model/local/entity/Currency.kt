package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat
import kotlin.math.ceil

data class Currency(
    val currency: String?,
    private val price: Double,
    @SerializedName("old_price")
    private val oldPrice: Double,
    val discount: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt()
    )

    fun getMoneyFormat() = "${getPrice().moneyFormat()} $currency"

    fun getOldPriceFormat() = "${getOldPrice().moneyFormat()} $currency"

    fun getPrice() = ceil(price).toInt()

    fun getOldPrice() = ceil(oldPrice).toInt()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency)
        parcel.writeDouble(price)
        parcel.writeDouble(oldPrice)
        parcel.writeInt(discount)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Currency> {
        override fun createFromParcel(parcel: Parcel) = Currency(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Currency>(size)
    }
}