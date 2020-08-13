package uz.mod.templatex.model.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import uz.mod.templatex.model.local.entity.profile.ProfileRegion

class ProfileRegionConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToProfileRegion(data: String?): ProfileRegion? {
        return if (data == null) null else gson.fromJson(data, ProfileRegion::class.java)
    }

    @TypeConverter
    fun profileRegionToString(data: ProfileRegion?): String? {
        return if (data == null) null else gson.toJson(data)
    }
}