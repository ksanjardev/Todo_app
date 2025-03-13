package uz.sanjar.androidweekexam33.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.sanjar.androidweekexam33.data.local.dao.SubTaskDao
import uz.sanjar.androidweekexam33.data.local.dao.TodoDao
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity

/**   Created by Sanjar Karimov 4:06 PM 1/19/2025   */

@Database(entities = [TodoEntity::class, SubTaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun subtaskDao(): SubTaskDao
}