package uz.uzmobile.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SubCategory(
    val id: Int,
    val name: String?,
    val slug: String?,
    val parent_id: Int,
    @SerializedName("is_multicurrency") val isMulticurrency: Boolean,
    @SerializedName("is_menu_shown") val isMenuShown: Int,
    val image: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(slug)
        parcel.writeInt(parent_id)
        parcel.writeByte(if (isMulticurrency) 1 else 0)
        parcel.writeInt(isMenuShown)
        parcel.writeString(image)
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