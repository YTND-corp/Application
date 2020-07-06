package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mod.templatex.model.local.entity.profile.ProfileAddress

@Dao
interface ProfileAddressDao {
    @Query("SELECT * FROM profile_addresses")
    fun getAll(): LiveData<List<ProfileAddress>>

    @Query("SELECT * from profile_addresses WHERE id=:id")
    fun get(id: Int): LiveData<ProfileAddress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(addresses: List<ProfileAddress>)

    @Query("DELETE FROM profile_addresses")
    fun deleteAll()
}