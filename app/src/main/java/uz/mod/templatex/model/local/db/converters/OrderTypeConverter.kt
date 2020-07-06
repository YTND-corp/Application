package uz.mod.templatex.model.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import uz.mod.templatex.model.local.entity.profile.State

class OrderTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToState(data: String?): State? {
        return if (data == null) null else gson.fromJson(data, State::class.java)
    }

    @TypeConverter
    fun stateToString(data: State?): String? {
        return if (data == null) null else gson.toJson(data)
    }
}