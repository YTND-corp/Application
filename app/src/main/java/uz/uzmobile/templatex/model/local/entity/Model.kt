package uz.uzmobile.templatex.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("model_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo_sha")
    val photoSha: String,
    @SerializedName("modifications")
    var modifications: ArrayList<Modification>,
    @SerializedName("colors")
    var colors: ArrayList<Color>) {
}