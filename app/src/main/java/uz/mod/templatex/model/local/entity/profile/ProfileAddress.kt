package uz.mod.templatex.model.local.entity.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profile_addresses")
class ProfileAddress(
    @PrimaryKey
    val id: Int,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val phone: String?,
    val region: String?,
    val city: String?,
    val street: String?,
    val building: String?,
    val entry: String?,
    val postcode: String?,
    @SerializedName("is_default")
    val isDefault: Boolean?
) {
    fun getFullName() = "$firstName $lastName"

    fun getContact() = "$firstName - $phone"

    fun getAddress() = arrayOf(street, building, entry, city).filterNotNull().joinToString()

    fun getStreetBuildingEntry() = arrayOf(street,building, entry).filterNotNull().joinToString()
}