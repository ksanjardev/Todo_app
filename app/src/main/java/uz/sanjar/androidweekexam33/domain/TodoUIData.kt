package uz.sanjar.androidweekexam33.domain

import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity
import uz.sanjar.androidweekexam33.utils.task_type.TaskType

/**   Created by Sanjar Karimov 4:37 PM 1/20/2025   */

data class TodoUIData(
    val id: Long,
    val task: String,
    val taskType: TaskType,
    val time: String,
    val hasSubTask: Boolean,
    val checked: Boolean,
    val subList: List<SubTaskEntity> = emptyList(),
)


fun TodoUIData.toTodoEntity(): TodoEntity = TodoEntity(
    id = id,
    checked = checked,
    task = task,
    hasSubTask = hasSubTask,
    time = time,
    taskType = taskType
)