package uz.sanjar.androidweekexam33.ui.screens.view_task

import kotlinx.coroutines.flow.StateFlow
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 6:28 PM 1/20/2025   */

interface EditContract {

    sealed interface Intent {
        data class OnUpdateClick(val todoUIData: TodoUIData): Intent
        data class OnAddNewSub(val todoUIData: TodoUIData): Intent
        data object OnBackClick: Intent
    }

    data class UIState(
        val todoUIData: TodoUIData? = null,
    )

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun eventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun openHome()
        suspend fun back()
    }

}