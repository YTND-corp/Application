package uz.mod.templatex.model.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.mod.templatex.model.remote.response.Pagination

@Entity(tableName = "filters")
data class Filter (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val brands: List<Brand>?,
    val attributes: List<Attribute>?,
    @Embedded
    var pagination: Pagination?
)

data class Attribute (
    val id: Int,
    val name: String,
    val values: List<Value>?
)

data class Value(
    val id: Int,
    val name: String
)