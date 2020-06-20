package uz.mod.templatex.model.local.entity

import com.google.gson.annotations.SerializedName

data class Currency(val price: Int, @SerializedName("old_price") val oldPrice: Int, val discount: Int, val currency: String?)