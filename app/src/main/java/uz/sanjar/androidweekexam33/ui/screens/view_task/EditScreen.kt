package uz.sanjar.androidweekexam33.ui.screens.view_task

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.sanjar.androidweekexam33.R
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData
import uz.sanjar.androidweekexam33.ui.screens.new_task.SubTaskField
import uz.sanjar.androidweekexam33.utils.task_type.TaskType
import java.time.format.DateTimeFormatter

/**   Created by Sanjar Karimov 6:25 PM 1/20/2025   */

class EditScreen(val todoUIData: TodoUIData) : Screen {
    @Composable
    override fun Content() {
        val viewModel: EditContract.ViewModel = getViewModel<EditViewModel>()
        EditScreenContent(
            eventDispatcher = viewModel::eventDispatcher,
            uiState = viewModel.uiState.collectAsState(),
            todoUIData = todoUIData
        )
    }

}


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenContent(
    eventDispatcher: (EditContract.Intent) -> Unit = {},
    uiState: State<EditContract.UIState> = remember { mutableStateOf(EditContract.UIState()) },
    todoUIData: TodoUIData? = null,
) {
    var task by remember { mutableStateOf(todoUIData!!.task) }
    var hasSubTask by remember { mutableStateOf(todoUIData!!.hasSubTask) }
    var selectedTaskType by remember { mutableStateOf(todoUIData!!.taskType) }
    val subTaskList = remember {
        mutableStateListOf<Pair<Boolean, String>>().apply {
            todoUIData!!.subList.forEachIndexed {index, subtask ->
                Log.d("LLL", "$index, -> ${subtask.checked}; text = ${subtask.subTask} ")
                add(Pair(subtask.checked, subtask.subTask))
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "${todoUIData?.time}", // Format in hh:mm AM/PM

                    )
                },
                actions = {
                    IconButton(
                        onClick = { eventDispatcher.invoke(EditContract.Intent.OnBackClick) },
                        modifier = Modifier.clip(shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(21.dp),
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    placeholder = {
                        Text(
                            text = "Write a new task",
                            fontSize = 30.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,

                        ),
                    value = task,
                    onValueChange = { task = it }
                )

                if (hasSubTask) {
                    subTaskList.forEachIndexed { index, subtask ->
                        SubTaskField(data = subtask,
                            onTextChange = {
                                subTaskList[index] = subTaskList[index].copy(second = it)
                            },
                            onCheckChange = {
                                subTaskList[index] = subTaskList[index].copy(first = it)
                            })
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(horizontal = 16.dp)
                        .clickable {
                            hasSubTask = true
                            subTaskList.add(Pair(false, ""))
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                        tint = Color.Black,
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Add subtask")
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .imePadding()
                    .padding(bottom = 50.dp)
            ) {
                LazyRow {
                    items(TaskType.entries.size) {
                        val isSelected = selectedTaskType == TaskType.entries[it]
                        Text(
                            text = TaskType.entries[it].taskName,
                            fontSize = 12.sp,
                            color = if (isSelected) selectedTaskType.color else Color.Gray,
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) selectedTaskType.backgroundColor else Color.LightGray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { selectedTaskType = TaskType.entries[it] }
                                .padding(horizontal = 5.dp, vertical = 3.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {

                            val newSubTasks = if (hasSubTask) {
                                subTaskList
                                    .filter { it.second.isNotEmpty() || it.second.isNotBlank() }
                                    .mapIndexed { index, qq ->
                                        SubTaskEntity(
                                            id = if (index < todoUIData?.subList?.size!!) {
                                                todoUIData.subList[index].id
                                            } else {
                                                0
                                            },
                                            checked = qq.first,
                                            subTask = qq.second,
                                            todoId = todoUIData.id
                                        )
                                    }
                            } else emptyList()


                            eventDispatcher.invoke(
                                EditContract.Intent.OnAddNewSub(
                                    TodoUIData(
                                        id = todoUIData!!.id,
                                        task = task,
                                        taskType = selectedTaskType,
                                        time = todoUIData.time,
                                        hasSubTask = hasSubTask,
                                        checked = todoUIData.checked,
                                        subList = newSubTasks
                                    )
                                )
                            )

                            eventDispatcher.invoke(
                                EditContract.Intent.OnUpdateClick(
                                    TodoUIData(
                                        id = todoUIData.id,
                                        task = task,
                                        taskType = selectedTaskType,
                                        time = todoUIData.time,
                                        hasSubTask = hasSubTask,
                                        checked = todoUIData.checked,
                                    )
                                )
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(47.dp),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = Color.LightGray,
                            containerColor = Color(0xFF393433)
                        )
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun EditScreenPreview() {
    EditScreenContent()
}