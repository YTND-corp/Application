package uz.uzmobile.templatex.model.local.db.dao

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//@Dao
//interface ItemDao {

//    @Query("SELECT * from message")
//    fun getMessageList(): LiveData<List<Item>>
//
//    @Query("SELECT * from message WHERE id=:id")
//    fun getMessageById(id: Int): Message
//
//    @Query("SELECT COUNT(*) FROM message WHERE isRead=0")
//    fun getUnreadMessages(): LiveData<Int>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(messageList: List<Message>)
//
//    @Query("UPDATE message SET isRead=1 WHERE id=:id")
//    fun setMessageReadById(id: Int)
//
//    @Query("DELETE FROM message")
//    fun deleteAll()
//
//    @Query("DELETE FROM message WHERE id=:id")
//    fun deleteMessageById(id: Int)
//}