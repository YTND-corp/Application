package uz.uzmobile.templatex.model.local.entity

import com.google.gson.annotations.SerializedName


data class News(
    val title: String,
    val text: String,
    @SerializedName("created_on")
    val created: String){
}