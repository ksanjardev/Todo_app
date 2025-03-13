package uz.sanjar.androidweekexam33.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.sanjar.androidweekexam33.data.local.dao.SubTaskDao
import uz.sanjar.androidweekexam33.data.local.dao.TodoDao
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData
import uz.sanjar.androidweekexam33.domain.toTodoEntity
import javax.inject.Inject
import javax.inject.Singleton

/**   Created by Sanjar Karimov 4:25 PM 1/19/2025   */

@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val subTaskDao: SubTaskDao,
) : TodoRepository {
    override fun addTodo(data: TodoUIData): Flow<Result<Unit>> = flow {
        val todoId = todoDao.addTodo(data.toTodoEntity())

        Log.d("jjj", "addTodo: repository ${todoId}")
        if (data.subList.isNotEmpty())
            data.subList.forEach {
                subTaskDao.insertSubtasks(
                    SubTaskEntity(
                        id = 0,
                        todoId = todoId,
                        checked = it.checked,
                        subTask = it.subTask
                    )
                )
                Log.d("jjj", "${it.todoId}")
            }
        emit(Result.success(Unit))
    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    /*
        override fun addSubTask(data: List<SubTaskEntity>): Flow<Result<Unit>> = flow {
            subTaskDao.addSubtasks(data)
            emit(Result.success(Unit))
        }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }
    */

    override fun getTodoList(): Flow<Result<List<TodoUIData>>> = flow {
        todoDao.getTodoList().collect {
            emit(
                Result.success(
                    it.map { todo ->
                        Log.d("sss", "hasSubtask = ${todo.hasSubTask}")
                        TodoUIData(
                            id = todo.id,
                            task = todo.task,
                            taskType = todo.taskType,
                            time = todo.time,
                            hasSubTask = todo.hasSubTask,
                            checked = todo.checked,
                            subList = subTaskDao.getSubtaskById(todo.id)
                        )

                    }
                )
            )
        }
    }
        .flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    override fun updateSubtask(data: SubTaskEntity): Flow<Result<Unit>> = flow {
        subTaskDao.updateSubtask(data)
        emit(Result.success(Unit))
    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    override fun updateTodoTask(data: TodoUIData): Flow<Result<Unit>> = flow {
        todoDao.updateTodo(data.toTodoEntity())
        emit(Result.success(Unit))
    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    override fun addSubtasks(data: TodoUIData): Flow<Result<Unit>> = flow {
        val todoId = todoDao.addTodo(data.toTodoEntity())
        if (data.subList.isNotEmpty()) {
            subTaskDao.addSubtasks(data.subList)

        }
        emit(Result.success(Unit))
    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    override fun getTodoForDate(date: String): Flow<Result<List<TodoUIData>>> = flow {
        todoDao.getTodoListForDate(date).collect {
            Log.d("DAY", "getTodoForDate: repository ${it.size}")
            emit(
                Result.success(
                    it.map { todo ->
                        Log.d("sss", "hasSubtask = ${todo.hasSubTask}")
                        TodoUIData(
                            id = todo.id,
                            task = todo.task,
                            taskType = todo.taskType,
                            time = todo.time,
                            hasSubTask = todo.hasSubTask,
                            checked = todo.checked,
                            subList = subTaskDao.getSubtaskById(todo.id)
                        )

                    }
                )
            )
        }
    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    override fun deleteTodo(todoEntity: TodoUIData): Flow<Result<Unit>> = flow {
        todoDao.deleteTodo(todoEntity.toTodoEntity())
        emit(Result.success(Unit))
    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }


}