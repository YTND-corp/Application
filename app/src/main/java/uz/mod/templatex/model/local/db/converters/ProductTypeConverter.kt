package uz.mod.templatex.model.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mod.templatex.model.local.entity.*
import uz.mod.templatex.model.local.entity.Currency
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
    fun stringToValueList(data: String?): List<AttributeValue>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<AttributeValue>?>() {}.type
        return gson.fromJson<List<AttributeValue>>(data, listType)
    }

    @TypeConverter
    fun valueListToString(someObjects: List<AttributeValue>?): String? {
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

    @TypeConverter
    fun stringToAttrList(data: String?): List<FilterAttribute>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<FilterAttribute>?>() {}.type
        return gson.fromJson<List<FilterAttribute>>(data, listType)
    }

    @TypeConverter
    fun attrListToString(someObjects: List<FilterAttribute>?): String? {
        return gson.toJson(someObjects)
    }
}