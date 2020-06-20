package uz.mod.templatex.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.mod.templatex.model.local.db.dao.ProductDao
import uz.mod.templatex.model.local.entity.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}