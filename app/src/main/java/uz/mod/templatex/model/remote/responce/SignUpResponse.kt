package uz.mod.templatex.model.remote.responce

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    val id: Int,
    @SerializedName("first_name")
    @Expose
    val name: String,
    @SerializedName("last_name")
    @Expose
    val surname: String,
    val email: String,
    val country: String,
    val phone: String,
    @SerializedName("access_token")
    @Expose
    val token: String?,
    @SerializedName("token_type")
    @Expose
    val tokenType: String?,
    @SerializedName("expires_in")
    @Expose
    val expiresIn: Long
)