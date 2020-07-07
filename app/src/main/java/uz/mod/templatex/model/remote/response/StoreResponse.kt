package uz.mod.templatex.model.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.User

data class StoreResponse(val user: User?, val date: String?, val notifications: Boolean): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeString(date)
        parcel.writeByte(if (notifications) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoreResponse> {
        override fun createFromParcel(parcel: Parcel): StoreResponse {
            return StoreResponse(parcel)
        }

        override fun newArray(size: Int): Array<StoreResponse?> {
            return arrayOfNulls(size)
        }
    }

}
