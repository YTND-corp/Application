package uz.mod.templatex.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.mod.templatex.model.local.db.converters.ProductTypeConverter
import uz.mod.templatex.model.local.db.dao.FilterDao
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.local.entity.Product

@Database(entities = [Product::class, Filter::class], version = 1, exportSchema = false)
@TypeConverters(ProductTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun filterDao(): FilterDao
}