package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable

data class HomeGender(val id: Int, val name: String?, val image: String?, val items: List<HomeItem>?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(HomeItem)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeGender> {
        override fun createFromParcel(parcel: Parcel): HomeGender {
            return HomeGender(parcel)
        }

        override fun newArray(size: Int): Array<HomeGender?> {
            return arrayOfNulls(size)
        }
    }

}