package uz.sanjar.androidweekexam33.data.local

/**   Created by Sanjar Karimov 5:03 PM 1/19/2025   */

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromSubTaskList(subTasks: List<SubTaskEntity>): String {
        return gson.toJson(subTasks)
    }

    @TypeConverter
    fun toSubTaskList(subTasks: String): List<SubTaskEntity> {
        val listType = object : TypeToken<List<SubTaskEntity>>() {}.type
        return gson.fromJson(subTasks, listType)
    }
}
