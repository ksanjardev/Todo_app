package uz.sanjar.androidweekexam33.ui.screens.new_task

import kotlinx.coroutines.flow.StateFlow
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 2:09 PM 1/19/2025   */
interface NewTaskContract {
    sealed interface Intent {
        data object OnBackClick : Intent
        data class OnSaveClick(val data: TodoUIData): Intent
    }

    data class UIState(
        val todoId: Long = 0
    )

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun eventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun back()
        suspend fun openHome()
    }

}