package uz.sanjar.androidweekexam33.ui.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.sanjar.androidweekexam33.data.TodoRepository
import uz.sanjar.androidweekexam33.domain.TodoUIData
import uz.sanjar.androidweekexam33.utils.task_type.TaskType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**   Created by Sanjar Karimov 1:57 PM 1/19/2025   */

@RequiresApi(Build.VERSION_CODES.O)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDirection: HomeDirection,
    private val todoRepository: TodoRepository,
) : HomeContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(HomeContract.UIState())

    override fun eventDispatcher(intent: HomeContract.Intent) {
        when (intent) {
            HomeContract.Intent.OnAddBtnClick -> viewModelScope.launch { homeDirection.openNewTaskScreen() }
            HomeContract.Intent.OnCalendarClick -> viewModelScope.launch { homeDirection.openCalendar() }
            is HomeContract.Intent.OnTaskCheckedChange -> {
                todoRepository.updateTodoTask(intent.data)
                    .onEach { result ->
                        result.onSuccess { }
                        result.onFailure { }
                    }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.OnEditClick -> viewModelScope.launch {
                homeDirection.openEditScreen(
                    intent.todoEntity
                )
            }

            is HomeContract.Intent.OnSubtaskCheckedChange -> {
                todoRepository.updateSubtask(intent.subtask)
                    .onEach { result ->
                        result.onSuccess { }
                        result.onFailure { }
                    }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.OnDeleteSwipe -> deleteTodo(intent.todoUIData)
        }
    }

    /*  init {
          getTodoList()
      }
  */
    init {
        val formatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)
        val todayDate = LocalDate.now().format(formatter)

        fetchListForDate(todayDate)
    }

    private fun deleteTodo(todoUIData: TodoUIData) {
        todoRepository.deleteTodo(todoUIData)
            .onEach { result ->
                result.onSuccess { }
                result.onFailure { }
            }.launchIn(viewModelScope)
    }

    private fun fetchListForDate(date: String) {
        Log.d("DAY", "date = $date")
        todoRepository.getTodoForDate(date)
            .onEach { result ->
                result.onSuccess { list ->
                    Log.d("DAY", "list = ${list.size}")
                    reduce { data ->
                        data.copy(todoList = list,
                            workCount = list.count { it.taskType == TaskType.WORK },
                            healthCount = list.count { it.taskType == TaskType.HEALTH },
                            mentalCount = list.count { it.taskType == TaskType.MENTAL_HEALTH },
                            otherCount = list.count { it.taskType == TaskType.OTHERS })
                    }
                }
                result.onFailure { }
            }.launchIn(viewModelScope)
    }

    @Deprecated("This method gets all the tasks this is not filtered for only take today's tasks")
    private fun getTodoList() {
        todoRepository.getTodoList()
            .onEach { result ->
                result.onSuccess { list ->
                    reduce { data ->
                        data.copy(
                            todoList = list,
                            workCount = list.count { it.taskType == TaskType.WORK },
                            healthCount = list.count { it.taskType == TaskType.HEALTH },
                            mentalCount = list.count { it.taskType == TaskType.MENTAL_HEALTH },
                            otherCount = list.count { it.taskType == TaskType.OTHERS }
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun reduce(block: (HomeContract.UIState) -> HomeContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}