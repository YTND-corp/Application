package uz.mod.templatex.model.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.w3c.dom.Attr
import uz.mod.templatex.model.local.entity.*
import uz.mod.templatex.model.local.entity.Currency
import java.util.*

class Converter {

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
    fun attributeListToString(someObjects: List<Attribute>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToAttributeList(data: String?): List<Attribute>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Attribute>?>() {}.type
        return gson.fromJson<List<Attribute>>(data, listType)
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
    fun stringToAttributeCombinationList(data: String?): List<AttributeCombination>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<AttributeCombination>?>() {}.type
        return gson.fromJson<List<AttributeCombination>>(data, listType)
    }

    @TypeConverter
    fun attributeCombinationListToString(someObjects: List<AttributeCombination>?): String? {
        return gson.toJson(someObjects)
    }
}