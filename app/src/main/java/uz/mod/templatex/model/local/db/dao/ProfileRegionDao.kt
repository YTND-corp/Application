package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mod.templatex.model.local.entity.profile.ProfileRegion

@Dao
interface ProfileRegionDao {
    @Query("SELECT * FROM profile_regions")
    fun getAll(): LiveData<List<ProfileRegion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(regions: List<ProfileRegion>)

    @Query("DELETE FROM profile_regions")
    fun deleteAll()
}