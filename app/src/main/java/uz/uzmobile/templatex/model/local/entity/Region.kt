package uz.uzmobile.templatex.model.local.entity

import com.google.gson.annotations.SerializedName

data class Region (
    @SerializedName("region_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dealers")
    val dealers: List<Dealer>) {
}