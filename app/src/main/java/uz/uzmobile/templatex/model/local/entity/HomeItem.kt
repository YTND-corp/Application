package uz.uzmobile.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HomeItem(
    val id: Int,
    @SerializedName("sort_order") val order: Int,
    val block: String?,
    @SerializedName("item_type") val type: String?,
    val title: String?,
    val image: String?,
val items: List<HomeSubItem>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(HomeSubItem)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(order)
        parcel.writeString(block)
        parcel.writeString(type)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeItem> {
        override fun createFromParcel(parcel: Parcel): HomeItem {
            return HomeItem(parcel)
        }

        override fun newArray(size: Int): Array<HomeItem?> {
            return arrayOfNulls(size)
        }
    }

    fun isBanner() = block.equals("component_001")
}

data class HomeSubItem(val id: Int, val name: String?, val image: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeSubItem> {
        override fun createFromParcel(parcel: Parcel): HomeSubItem {
            return HomeSubItem(parcel)
        }

        override fun newArray(size: Int): Array<HomeSubItem?> {
            return arrayOfNulls(size)
        }
    }

}