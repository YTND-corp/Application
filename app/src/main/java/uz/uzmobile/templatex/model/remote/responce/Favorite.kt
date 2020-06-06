package uz.uzmobile.templatex.model.remote.responce

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import uz.uzmobile.templatex.model.local.entity.Brand
import uz.uzmobile.templatex.model.local.entity.Category
import uz.uzmobile.templatex.model.local.entity.Currency

data class Favorite(
    val id: Int,
    val name: String?= null,
    val price: Int,
    @Ignore val currencies: List<Currency>? = listOf(),
    @SerializedName("isFavoritesAdded") val isFavorite: Boolean,
    val image: String?= null,
    val brand: Brand?= null,
    val category: Category? = null
)