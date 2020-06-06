package uz.uzmobile.templatex.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.uzmobile.templatex.model.local.db.dao.ProductDao
import uz.uzmobile.templatex.model.local.entity.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}