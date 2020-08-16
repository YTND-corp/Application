package uz.mod.templatex.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.utils.extension.moneyFormat

@Entity(tableName = "favorites")
class Favorite(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerializedName("currency")
    val currencies: List<Currency>,
    val brand: String,
    @SerializedName("is_favorites_added")
    val isFavorite: Boolean,
    val image: String
) {
    fun priceFormatted() = "${currencies.first().getPrice().moneyFormat()} ${currencies.first().currency}"
    fun oldPriceFormatted() = "${currencies.first().getOldPrice().moneyFormat()} ${currencies.first().currency}"
    fun hasDiscount() = currencies.first().discount > 0
}