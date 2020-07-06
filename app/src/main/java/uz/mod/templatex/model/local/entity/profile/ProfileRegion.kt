package uz.mod.templatex.model.local.entity.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_regions")
class ProfileRegion(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val sort_order: Int?
)