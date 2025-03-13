package uz.sanjar.androidweekexam33.ui.screens.calendar

import kotlinx.coroutines.flow.StateFlow
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 12:11 PM 1/21/2025   */

interface CalendarContract {

    sealed interface Intent {
        data class OnDateSelected(val date: String) : Intent
    }

    data class UIState(
        val todoList: List<TodoUIData> = emptyList(),
    )

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun eventDispatcher(intent: Intent)
    }

    interface Direction {

    }

}