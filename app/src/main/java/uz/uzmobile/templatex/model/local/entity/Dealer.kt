package uz.uzmobile.templatex.model.local.entity

import com.google.gson.annotations.SerializedName

data class Dealer (
    @SerializedName("dealer_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("phone_number")
    var phone: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("photo_sha")
    val photoSha: String,
    @SerializedName("lat_lng")
    val latLng: String) {
}