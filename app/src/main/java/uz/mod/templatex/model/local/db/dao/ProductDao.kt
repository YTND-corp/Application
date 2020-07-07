package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.mod.templatex.model.local.entity.Product


@Dao
interface ProductDao {
    @Query("SELECT * from products")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT * from products WHERE id=:id")
    fun getLiveProduct(id: Int): LiveData<Product>

    @Query("SELECT * from products WHERE id=:id")
    fun get(id: Int): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: Product)

    @Query("UPDATE products SET isFavorite=:isFavorite  WHERE id=:id")
    fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * from products WHERE isFavorite=:isFavorite")
    fun getFavorites(isFavorite: Boolean = true):  LiveData<List<Product>>

    @Query("DELETE FROM products")
    fun deleteAll()

    @Query("DELETE FROM products where id in (:ids)")
    fun delete(ids: List<Int>)

    @Query("DELETE FROM products WHERE id=:id")
    fun delete(id: Int)

    @Query("UPDATE products SET quantity=:quantity  WHERE cartProductId=:id")
    fun updateCount(id: Int, quantity: Int)

    @Query("UPDATE products SET selected=:selected  WHERE id=:id")
    fun setSelect(id: Int, selected: Boolean)
}