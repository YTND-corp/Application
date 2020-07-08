package uz.mod.templatex.model.local.entity.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.Currency

@Entity(tableName = "profile_favorites")
class ProfileFavorite(
    @PrimaryKey
    val id: Int,
    val name: String,
    val currency: List<Currency>,
    val brand: String,
    @SerializedName("is_favorites_added")
    val isFavoritesAdded: Boolean,
    val image: String
)