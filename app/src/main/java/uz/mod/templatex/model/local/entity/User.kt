package uz.mod.templatex.model.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    @SerializedName("first_name")
    val name: String?,
    @SerializedName("last_name")
    val surname: String?,
    val email: String?,
    val phone: String?,
    val gender: String?,
    val birthday: String?,
    val country: String?,
    @SerializedName("access_token")
    val token: String? //only used in checkout
) : Parcelable