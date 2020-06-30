package uz.mod.templatex.model.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class Brand(
    val id: Int,
    val name: String?,
    val slug: String?
) {
    @Ignore
    var selected : Boolean = false
}

