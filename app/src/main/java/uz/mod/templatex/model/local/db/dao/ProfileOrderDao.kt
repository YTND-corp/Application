package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mod.templatex.model.local.entity.profile.ProfileOrder

@Dao
interface ProfileOrderDao {
    @Query("SELECT * FROM profile_orders")
    fun getAll(): LiveData<List<ProfileOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(orders: List<ProfileOrder>)

    @Query("DELETE FROM profile_orders")
    fun deleteAll()
}