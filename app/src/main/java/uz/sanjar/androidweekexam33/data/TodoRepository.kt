package uz.sanjar.androidweekexam33.data

import kotlinx.coroutines.flow.Flow
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 4:24 PM 1/19/2025   */

interface TodoRepository {
    fun addTodo(data: TodoUIData): Flow<Result<Unit>>
//    fun addSubTask(data: List<SubTaskEntity>): Flow<Result<Unit>>
    fun getTodoList() : Flow<Result<List<TodoUIData>>>
    fun updateSubtask(data: SubTaskEntity): Flow<Result<Unit>>
    fun updateTodoTask(data: TodoUIData): Flow<Result<Unit>>
    fun addSubtasks(data: TodoUIData): Flow<Result<Unit>>
    fun getTodoForDate(date: String): Flow<Result<List<TodoUIData>>>
    fun deleteTodo(todoEntity: TodoUIData): Flow<Result<Unit>>
//    fun getSubtaskById(todoId: Long): Flow<Result<List<SubTaskEntity>>>
}