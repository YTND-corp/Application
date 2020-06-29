package uz.mod.templatex.model.local.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val currency: List<Currency>?,
    @SerializedName("is_favorites_added") val isFavorite: Boolean,
    val image: String?,
    val brand: String?,
    val categoryId: Int
) {
    fun getPrice() = currency?.first()?.getMoneyFormat()
}