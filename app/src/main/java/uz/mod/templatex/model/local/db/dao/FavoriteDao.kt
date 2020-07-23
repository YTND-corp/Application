package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mod.templatex.model.local.entity.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(orders: List<Favorite>)

    @Query("DELETE FROM favorites")
    fun deleteAll()

    @Query("DELETE FROM favorites WHERE id = :id")
    fun delete(id: Int)
}