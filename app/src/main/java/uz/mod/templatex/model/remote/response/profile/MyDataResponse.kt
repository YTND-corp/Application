package uz.mod.templatex.model.remote.response.profile

import com.google.gson.annotations.SerializedName
import java.util.*

data class MyDataResponse(
    val user: User,
    val genders: List<Gender>,
    val currencies: List<MyDataCurrency>,
    val languages: List<Language>
)

data class User(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val gender: GenderType,
    val birthday: String?,
    val email: String,
    val phone: String,
    val subscriptions: Boolean,
    val notifications: Boolean,
    val currency: Currency,
    val language: Language,
    val image: String
) {
    fun printFullName() = "$firstName $lastName"
}

data class Language(
    val id: Int,
    val name: String,
    @SerializedName("iso_code")
    val isoCode: String
)

data class MyDataCurrency(
    val id: Int,
    val name: String,
    val symbol: String,
    @SerializedName("iso_code")
    val isoCode: String
)

data class Gender(
    val type: GenderType,
    val name: String
)

enum class GenderType {
    @SerializedName("none")
    NONE,

    @SerializedName("male")
    MALE,

    @SerializedName("female")
    FEMALE;

    override fun toString(): String {
        return name.toLowerCase(Locale.ROOT)
    }
}
