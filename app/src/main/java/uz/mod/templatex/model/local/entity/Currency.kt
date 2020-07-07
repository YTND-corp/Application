package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat

data class Currency(
    @SerializedName("currency")
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
    ) {
    }

    fun getMoneyFormat() = "${price.moneyFormat()} $currency"
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency)
        parcel.writeInt(price)
        parcel.writeInt(oldPrice)
        parcel.writeInt(discount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Currency> {
        override fun createFromParcel(parcel: Parcel): Currency {
            return Currency(parcel)
        }

        override fun newArray(size: Int): Array<Currency?> {
            return arrayOfNulls(size)
        }
    }
}