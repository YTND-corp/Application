package uz.mod.templatex.model.local.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String?,
    @SerializedName("currency")val currencies: List<Currency>?,
    @SerializedName("is_favorites_added") val isFavorite: Boolean,
    var selected: Boolean = false,
    val image: String?,
    val brand: String?,
    val category: String?,
    val categoryId: Int?,
    val quantity: Int = 0,
    @SerializedName("cart_product_id") val cartProductId: Int = 0,
    @SerializedName("attribute_combination") val combinations: List<AttributeCombination>?
) {
    fun getPrice() = currencies?.first()?.price
    fun quantityText() = "$quantity"
    fun subtitle() = "${brand} - ${category}"
    fun priceFormatted() = "${currencies?.first()?.price?.moneyFormat()} ${currencies?.first()?.currency}"
    fun totalPrice() = quantity * (currencies?.first()?.price ?: 0)
    fun totalPriceFormatted() = totalPrice().moneyFormat() + " ${currencies?.first()?.currency}"
}

data class AttributeCombination(val key: String?, val value: String?)