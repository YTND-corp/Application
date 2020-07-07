package uz.mod.templatex.model.remote.request

import android.os.Parcel
import android.os.Parcelable
import uz.mod.templatex.model.local.entity.City
import uz.mod.templatex.model.remote.response.Delivery

data class StoreRequest(
    val phone:String?,
    val region: City?,
    val street: String? = null,
    var home: String?= null,
    var flat: String?= null,
    var comment: String?= null,
    var delivery: Delivery? = null,
    var paymentMethod: String?= null,
    var paymentProvider: String?= null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(City::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Delivery::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(phone)
        parcel.writeParcelable(region, flags)
        parcel.writeString(street)
        parcel.writeString(home)
        parcel.writeString(flat)
        parcel.writeString(comment)
        parcel.writeParcelable(delivery, flags)
        parcel.writeString(paymentMethod)
        parcel.writeString(paymentProvider)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoreRequest> {
        override fun createFromParcel(parcel: Parcel): StoreRequest {
            return StoreRequest(parcel)
        }

        override fun newArray(size: Int): Array<StoreRequest?> {
            return arrayOfNulls(size)
        }
    }


}