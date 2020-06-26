package uz.mod.templatex.model.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val price: String,
    @SerializedName("isFavoritesAdded") val isFavorite: Boolean,
    val image: String?,
    val brand: String?,
    val categoryId:Int
) {
}