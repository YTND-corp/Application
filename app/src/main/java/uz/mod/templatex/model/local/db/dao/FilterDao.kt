package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mod.templatex.model.local.entity.Filter

@Dao
interface FilterDao {
    @Query("SELECT * from filters")
    fun getAll(): LiveData<List<Filter>>

    @Query("SELECT * from filters WHERE id=:id")
    fun get(id: Int): Filter

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(filter: Filter)

    @Query("DELETE FROM filters")
    fun deleteAll()
}