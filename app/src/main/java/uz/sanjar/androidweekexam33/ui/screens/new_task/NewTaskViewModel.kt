package uz.sanjar.androidweekexam33.ui.screens.new_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import uz.sanjar.androidweekexam33.data.TodoRepository
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData
import javax.inject.Inject

/**   Created by Sanjar Karimov 1:57 PM 1/19/2025   */

@HiltViewModel
class NewTaskViewModel @Inject constructor(
    private val newTaskDirection: NewTaskContract.Direction,
    private val todoRepository: TodoRepository,
) : NewTaskContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(NewTaskContract.UIState())

    override fun eventDispatcher(intent: NewTaskContract.Intent) {
        when (intent) {
            NewTaskContract.Intent.OnBackClick -> viewModelScope.launch { newTaskDirection.back() }
            is NewTaskContract.Intent.OnSaveClick -> addTodo(intent.data)
        }
    }

    private fun addTodo(data: TodoUIData) {
        Log.d("SSS", "addTodo: viewModel ${data.subList}")
        todoRepository.addTodo(data)
            .onEach { result ->
                result.onSuccess {
                    newTaskDirection.openHome()
                }
                result.onFailure {  }
            }
            .launchIn(viewModelScope)

    }

    private fun reduce(block: (NewTaskContract.UIState) -> NewTaskContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}