package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat

data class Currency(
    val currency: String?,
    val price: Int,
    @SerializedName("old_price")
    val oldPrice: Int,
    val discount: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    fun getMoneyFormat() = "${price.moneyFormat()} $currency"

    fun getOldPriceFormat() = "${oldPrice.moneyFormat()} $currency"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency)
        parcel.writeInt(price)
        parcel.writeInt(oldPrice)
        parcel.writeInt(discount)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Currency> {
        override fun createFromParcel(parcel: Parcel) = Currency(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Currency>(size)
    }
}