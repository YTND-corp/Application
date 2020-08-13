package uz.mod.templatex.model.local.entity.profile

import androidx.room.Embedded
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
    val region: ProfileRegion?,
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

    override fun toString(): String {
        return "id $id firstName $firstName lastName $lastName" +
                " phone $phone city $city street $street" +
                " regionName ${region?.name} regionID ${region?.id}"+
                " building $building entry $entry postcode $postcode" +
                " isDefault $isDefault"
    }
}
