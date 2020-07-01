package uz.mod.templatex.model.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import uz.mod.templatex.model.remote.response.Pagination

@Entity(tableName = "filters")
data class Filter (
    @PrimaryKey
    var id: Int,
    val brands: List<Brand>?,
    val attributes: List<FilterAttribute>?,
    @Embedded
    var pagination: Pagination?
)


data class FilterAttribute (
    val id: Int,
    val name: String,
    val values: List<AttributeValue>?
)

data class AttributeValue(
    val id: Int,
    val name: String
){
    @Ignore
    var selected : Boolean = false
}