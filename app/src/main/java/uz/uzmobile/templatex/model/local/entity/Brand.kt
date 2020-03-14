package uz.uzmobile.templatex.model.local.entity

import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("filial_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("logo_sha")
    var logo_sha: String,
    @SerializedName("note")
    val note: String) {
}

