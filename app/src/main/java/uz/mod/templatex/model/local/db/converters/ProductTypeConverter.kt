package uz.mod.templatex.model.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.local.entity.Currency
import uz.mod.templatex.model.local.entity.Value
import java.util.*

class ProductTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToCurrencyList(data: String?): List<Currency>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Currency>?>() {}.type
        return gson.fromJson<List<Currency>>(data, listType)
    }

    @TypeConverter
    fun currencyListToString(someObjects: List<Currency>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToValueList(data: String?): List<Value>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Value>?>() {}.type
        return gson.fromJson<List<Value>>(data, listType)
    }

    @TypeConverter
    fun valueListToString(someObjects: List<Value>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToBrandList(data: String?): List<Brand>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Brand>?>() {}.type
        return gson.fromJson<List<Brand>>(data, listType)
    }

    @TypeConverter
    fun brandListToString(someObjects: List<Brand>?): String? {
        return gson.toJson(someObjects)
    }
}