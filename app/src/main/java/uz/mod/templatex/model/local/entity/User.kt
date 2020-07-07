package uz.mod.templatex.model.local.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("first_name")
    @Expose
    val name: String?,
    @SerializedName("last_name")
    @Expose
    val surname: String?,
    val email: String?,
    val country: String?,
    val phone: String?,
    @SerializedName("access_token")
    @Expose
    val token: String?,
    @SerializedName("token_type")
    @Expose
    val tokenType: String?,
    @SerializedName("expires_in")
    @Expose
    val expiresIn: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(email)
        parcel.writeString(country)
        parcel.writeString(phone)
        parcel.writeString(token)
        parcel.writeString(tokenType)
        parcel.writeLong(expiresIn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}