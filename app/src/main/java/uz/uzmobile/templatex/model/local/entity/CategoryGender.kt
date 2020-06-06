package uz.uzmobile.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CategoryGender(
    val id: Int,
    val name: String?,
    val image: String?,
    @SerializedName("children") val categories: List<Category>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Category)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeTypedList(categories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryGender> {
        override fun createFromParcel(parcel: Parcel): CategoryGender {
            return CategoryGender(parcel)
        }

        override fun newArray(size: Int): Array<CategoryGender?> {
            return arrayOfNulls(size)
        }
    }

}