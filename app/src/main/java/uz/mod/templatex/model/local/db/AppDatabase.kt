package uz.mod.templatex.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.mod.templatex.model.local.db.converters.Converter
import uz.mod.templatex.model.local.db.converters.OrderTypeConverter
import uz.mod.templatex.model.local.db.dao.*
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.local.entity.profile.ProfileAddress
import uz.mod.templatex.model.local.entity.profile.ProfileFavorite
import uz.mod.templatex.model.local.entity.profile.ProfileOrder
import uz.mod.templatex.model.local.entity.profile.ProfileRegion

@Database(entities = [Product::class, Filter::class, ProfileAddress::class, ProfileRegion::class, ProfileOrder::class, ProfileFavorite::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class, Converter::class, OrderTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun filterDao(): FilterDao
    abstract fun profileAddressDao(): ProfileAddressDao
    abstract fun profileRegionDao(): ProfileRegionDao
    abstract fun profileOrderDao(): ProfileOrderDao
    abstract fun profileFavoriteDao(): ProfileFavoriteDao
}