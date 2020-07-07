package uz.mod.templatex.model.remote.network

import com.google.gson.annotations.SerializedName

data class ApiError (
    @SerializedName("error_code") var code: Int,
    @SerializedName("title") var title: String?,
    @SerializedName("description") var message: String)