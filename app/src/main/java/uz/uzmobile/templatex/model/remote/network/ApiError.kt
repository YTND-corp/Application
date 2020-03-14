package uz.uzmobile.templatex.model.remote.network

import com.google.gson.annotations.SerializedName

data class ApiError (
    @SerializedName("errorCode") var code: Int,
    @SerializedName("title") var title: String?,
    @SerializedName("subTitle") var message: String)