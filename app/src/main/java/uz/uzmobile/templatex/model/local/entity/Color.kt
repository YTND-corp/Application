package uz.uzmobile.templatex.model.local.entity


import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("color_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("hex_value")
    val hex: String,
    @SerializedName("note")
    var note: String,
    @SerializedName("photo_sha")
    val photoSha: String,
    @SerializedName("color_price")
    val price: String
    )