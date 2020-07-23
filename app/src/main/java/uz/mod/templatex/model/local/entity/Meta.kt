package uz.mod.templatex.model.local.entity

import com.google.gson.annotations.SerializedName

class Meta(
    val token: String?,
    @SerializedName("token_type")
    val tokenType: String?,
    val ttl: Int?
)