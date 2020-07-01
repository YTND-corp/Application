package uz.mod.templatex.model.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class Brand (
    override val id: Int,
    override val name: String?,
    val slug: String?
) : IValue {
    @Ignore
    override var selected : Boolean = false
}

