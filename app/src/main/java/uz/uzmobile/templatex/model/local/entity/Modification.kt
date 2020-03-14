package uz.uzmobile.templatex.model.local.entity

import com.google.gson.annotations.SerializedName

data class Modification (
    @SerializedName("modification_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("producing")
    var producing: String,
    @SerializedName("horsepower")
    var horsepower: String,
    @SerializedName("acceleration")
    var acceleration: String,
    @SerializedName("transmission")
    var transmission: String,
    @SerializedName("descriptions")
    val descriptions: String) {
}