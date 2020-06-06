package uz.uzmobile.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.uzmobile.templatex.model.local.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * from product")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT * from product WHERE id=:id")
    fun get(id: Int): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: Product)

    @Query("UPDATE product SET isFavorite=:isFavorite  WHERE id=:id")
    fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Query("DELETE FROM product WHERE id=:id")
    fun delete(id: Int)
}