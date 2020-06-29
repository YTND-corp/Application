package uz.mod.templatex.model.local.entity

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat

data class Currency(
    @SerializedName("currency")
    val currency: String,
    val price: Int,
    @SerializedName("old_price")
    val oldPrice: Int,
    val discount: Int
) {
    fun getMoneyFormat() = "${price.moneyFormat()} $currency"
}