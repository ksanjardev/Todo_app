package uz.sanjar.androidweekexam33.ui.screens.home

import kotlinx.coroutines.flow.StateFlow
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 1:56 PM 1/19/2025   */
interface HomeContract {
    sealed interface Intent {
        data object OnAddBtnClick : Intent
        data class OnTaskCheckedChange(val data: TodoUIData) : Intent
        data class OnSubtaskCheckedChange(val subtask: SubTaskEntity) : Intent
        data class OnEditClick(val todoEntity: TodoUIData) : Intent
        data object OnCalendarClick : Intent
        data class OnDeleteSwipe(val todoUIData: TodoUIData): Intent
    }

    data class UIState(
        val todoList: List<TodoUIData> = emptyList(),
        val todoEntity: TodoUIData? = null,
        val healthCount: Int = 0,
        val mentalCount: Int = 0,
        val otherCount: Int = 0,
        val workCount: Int = 0,
    )

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun eventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun openNewTaskScreen()
        suspend fun openCalendar()
        suspend fun openEditScreen(todoUIData: TodoUIData)
    }
}