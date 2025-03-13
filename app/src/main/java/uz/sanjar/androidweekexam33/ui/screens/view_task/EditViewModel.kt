package uz.sanjar.androidweekexam33.ui.screens.view_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.sanjar.androidweekexam33.data.TodoRepository
import javax.inject.Inject

/**   Created by Sanjar Karimov 6:55 PM 1/20/2025   */

@HiltViewModel
class EditViewModel @Inject constructor(
    private val editDirection: EditContract.Direction,
    private val todoRepository: TodoRepository,
) : EditContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(EditContract.UIState())

    override fun eventDispatcher(intent: EditContract.Intent) {
        when (intent) {
            is EditContract.Intent.OnUpdateClick -> {
                Log.d("AAA", "EditScreenContent: viewModel ${intent.todoUIData.subList}\n")
                todoRepository.updateTodoTask(data = intent.todoUIData)
                    .onEach { result ->
                        result.onSuccess {
                            editDirection.openHome()
                        }
                        result.onFailure {
                            Log.d("VVV", "eventDispatcher: $it")
                        }
                    }.launchIn(viewModelScope)
            }

            is EditContract.Intent.OnBackClick -> {
                viewModelScope.launch {
                    editDirection.back()
                }
            }

            is EditContract.Intent.OnAddNewSub -> {
                Log.d("AAA", "eventDispatcher: worked")
                todoRepository.addSubtasks(intent.todoUIData)
                    .onStart {
                        Log.d("AAA", "eventDispatcher: start worked")
                    }
                    .onCompletion {
                        Log.d("AAA", "eventDispatcher: completion worked")
                    }
                    .onEach { result ->
                        result.onSuccess {
                            Log.d("AAA", "eventDispatcher: success worked")
                        }
                        result.onFailure {
                            Log.d("AAA", "eventDispatcher: failure worked")
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun reduce(block: (EditContract.UIState) -> EditContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}