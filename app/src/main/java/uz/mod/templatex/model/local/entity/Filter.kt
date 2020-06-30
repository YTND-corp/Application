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
    //TODO: Change to attr list
    @Embedded
    val attributes: Attribute?,
    @Embedded
    var pagination: Pagination?
)

data class Attribute(
    @Embedded(prefix = "color_") val color: FilterAttribute?
)

data class FilterAttribute (
    val id: Int,
    val name: String,
    val values: List<Value>?
)

data class Value(
    val id: Int,
    val name: String
){
    @Ignore
    var selected : Boolean = false
}