package uz.mod.templatex.model.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mod.templatex.model.local.entity.profile.ProfileFavorite

@Dao
interface ProfileFavoriteDao {
    @Query("SELECT * FROM profile_favorites")
    fun getAll(): LiveData<List<ProfileFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(orders: List<ProfileFavorite>)

    @Query("DELETE FROM profile_favorites")
    fun deleteAll()

    @Query("DELETE FROM profile_favorites WHERE id = :id")
    fun delete(id: Int)
}