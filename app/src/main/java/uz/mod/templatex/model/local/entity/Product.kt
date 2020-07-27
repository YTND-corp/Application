package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String?,
    @SerializedName("currency")val currencies: List<Currency>?,
    @SerializedName("is_favorites_added") var isFavorite: Boolean,
    var selected: Boolean = false,
    val image: String?,
    val brand: String?,
    val category: String?,
    val categoryId: Int?,
    val reference: String?,
    val quantity: Int = 0,
    @SerializedName("cart_product_id") val cartProductId: Int = 0,
    @SerializedName("attribute_combination") val combinations: List<AttributeCombination>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createTypedArrayList(Currency),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createTypedArrayList(AttributeCombination)
    )

    fun getPrice() = currencies?.first()?.price
    fun quantityText() = "$quantity"
    fun subtitle() = "$brand - $category"
    fun priceFormatted() = "${currencies?.first()?.price?.moneyFormat()} ${currencies?.first()?.currency}"
    fun oldPriceFormatted() = "${currencies?.first()?.oldPrice?.moneyFormat()} ${currencies?.first()?.currency}"
    fun totalPrice() = quantity * (currencies?.first()?.price ?: 0)
    fun totalPriceFormatted() = totalPrice().moneyFormat() + " ${currencies?.first()?.currency}"
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeTypedList(currencies)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeByte(if (selected) 1 else 0)
        parcel.writeString(image)
        parcel.writeString(brand)
        parcel.writeString(category)
        parcel.writeValue(categoryId)
        parcel.writeString(reference)
        parcel.writeInt(quantity)
        parcel.writeInt(cartProductId)
        parcel.writeTypedList(combinations)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel) = Product(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Product>(size)
    }
}

data class AttributeCombination(val key: String?, val value: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<AttributeCombination> {
        override fun createFromParcel(parcel: Parcel) = AttributeCombination(parcel)
        override fun newArray(size: Int) = arrayOfNulls<AttributeCombination>(size)
    }

}