package uz.sanjar.androidweekexam33.ui.screens.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.sanjar.androidweekexam33.data.TodoRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**   Created by Sanjar Karimov 3:36 PM 1/21/2025   */

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : CalendarContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(CalendarContract.UIState())

    override fun eventDispatcher(intent: CalendarContract.Intent) {
        when (intent) {
            is CalendarContract.Intent.OnDateSelected -> fetchListForDate(intent.date)
        }
    }

    init {
        val formatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)
        val todayDate = LocalDate.now().format(formatter)

        fetchListForDate(todayDate)
    }

    private fun fetchListForDate(date: String) {
        Log.d("DAY", "date = $date")
        todoRepository.getTodoForDate(date)
            .onEach { result ->
                result.onSuccess { list ->
                    Log.d("DAY", "list = ${list.size}")
                    reduce { it.copy(todoList = list) }
                }
                result.onFailure { }
            }.launchIn(viewModelScope)
    }

    private fun reduce(block: (CalendarContract.UIState) -> CalendarContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }

}