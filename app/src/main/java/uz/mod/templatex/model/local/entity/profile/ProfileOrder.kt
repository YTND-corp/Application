package uz.mod.templatex.model.local.entity.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profile_orders")
data class ProfileOrder(
    @PrimaryKey
    val id: Int,
    val reference: String,
    val quantity: Int,
    @SerializedName("total_price")
    val totalPrice: Double,
    val state: State?
) {
    fun getOrderId() = "№$reference"

    fun getPrice() = "$totalPrice UZS"
}

data class State(
    val type: StateType,
    val name: String,
    val time: String
)

enum class StateType(type: String) {
    INFO("info"),
    WARNING("warning"),
    SUCCESS("success"),
    DANGER("danger")
}
