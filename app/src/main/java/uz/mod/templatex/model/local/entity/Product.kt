package uz.mod.templatex.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val price: String,
    val isFavorite: Boolean,
    val image: String?,
    val brand: String?,
    val categoryId:Int
) {
}