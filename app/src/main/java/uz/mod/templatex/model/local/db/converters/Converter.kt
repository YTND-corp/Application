package uz.mod.templatex.model.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mod.templatex.model.local.entity.AttributeCombination
import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.local.entity.*
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