package uz.sanjar.androidweekexam33.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.sanjar.androidweekexam33.utils.task_type.TaskType

/**   Created by Sanjar Karimov 4:07 PM 1/19/2025   */

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val checked: Boolean = false,
    val task: String,
    val hasSubTask: Boolean = false,
    val time: String,
    val taskType: TaskType,
)



