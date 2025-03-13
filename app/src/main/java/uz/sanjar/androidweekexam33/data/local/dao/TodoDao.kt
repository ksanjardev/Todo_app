package uz.sanjar.androidweekexam33.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity

/**   Created by Sanjar Karimov 4:16 PM 1/19/2025   */

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoEntity): Long

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun getTaskById(id: Long): TodoEntity


    @Query("""SELECT * FROM TodoEntity WHERE SUBSTR(time, 1, 3) = :date""")
    fun getTodoListForDate(date: String): Flow<List<TodoEntity>>


    @Delete
    fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM TodoEntity")
    fun getTodoList(): Flow<List<TodoEntity>>

    @Update
    suspend fun updateTodo(todo: TodoEntity)

}