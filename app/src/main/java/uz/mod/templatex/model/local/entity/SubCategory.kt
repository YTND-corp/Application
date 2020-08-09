package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SubCategory(
    val id: Int,
    val name: String?,
    val slug: String?,
    @SerializedName("parent_id") val parentId: Int,
    @SerializedName("is_multicurrency") val isMulticurrency: Boolean,
    @SerializedName("is_menu_shown") val isMenuShown: Int,
    val image: String?,
    @SerializedName("is_sale")
    val isSale: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt() == 1
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(slug)
        parcel.writeInt(parentId)
        parcel.writeByte(if (isMulticurrency) 1 else 0)
        parcel.writeInt(isMenuShown)
        parcel.writeString(image)
        parcel.writeInt(if (isSale) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubCategory> {
        override fun createFromParcel(parcel: Parcel): SubCategory {
            return SubCategory(parcel)
        }

        override fun newArray(size: Int): Array<SubCategory?> {
            return arrayOfNulls(size)
        }
    }

}