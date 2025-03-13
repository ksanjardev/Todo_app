package uz.sanjar.androidweekexam33.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity

/**   Created by Sanjar Karimov 5:01 PM 1/19/2025   */

@Dao
interface SubTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubtasks(subtaskList: List<SubTaskEntity>)

    @Insert
    suspend fun insertSubtasks(subtask: SubTaskEntity)

    @Update
    suspend fun updateSubtask(subtaskList: SubTaskEntity)


    @Query("SELECT * FROM SubTaskEntity WHERE todoId = :todoId")
    fun getSubtaskById(todoId:Long) : List<SubTaskEntity>
}