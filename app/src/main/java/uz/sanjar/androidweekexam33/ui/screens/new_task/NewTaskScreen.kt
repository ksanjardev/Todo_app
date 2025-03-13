package uz.sanjar.androidweekexam33.ui.screens.new_task

import android.annotation.SuppressLint
import android.app.TimePickerDialog
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.sanjar.androidweekexam33.R
import uz.sanjar.androidweekexam33.broadcast_receiver.saveTaskAndScheduleAlarm
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData
import uz.sanjar.androidweekexam33.utils.task_type.TaskType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


/**   Created by Sanjar Karimov 1:55 PM 1/19/2025   */
class NewTaskScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: NewTaskContract.ViewModel = getViewModel<NewTaskViewModel>()

        NewTaskScreenContent(viewModel::eventDispatcher, viewModel.uiState.collectAsState())
    }
}


@SuppressLint("NewApi", "DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreenContent(
    eventDispatcher: (NewTaskContract.Intent) -> Unit = {},
    uiState: State<NewTaskContract.UIState> = remember { mutableStateOf(NewTaskContract.UIState()) },
) {
    var task by remember { mutableStateOf("") }

    var hasSubTask by remember { mutableStateOf(false) }
    var selectedTaskType by remember { mutableStateOf(TaskType.HEALTH) }
    val subTaskList = remember { mutableStateListOf<Pair<Boolean, String>>() }
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    // DatePicker State
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    // DatePicker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedTimeMillis = datePickerState.selectedDateMillis
                    if (selectedTimeMillis != null) {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = selectedTimeMillis
                        selectedDate = LocalDate.of(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        // After selecting date, show time picker
                        showTimePicker = true
                    }
                    showDatePicker = false
                }) {
                    Text(
                        text = "Ok",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // TimePicker Dialog
    if (showTimePicker && selectedDate != null) {
        TimePickerDialog(
            LocalContext.current,
            { _, hourOfDay, minute ->
                // Create LocalTime from selected hour and minute
                selectedTime = LocalTime.of(hourOfDay, minute)

                // Once time is selected, combine date and time into selectedDateTime
                selectedDate?.let { date ->
                    val localDateTime = LocalDateTime.of(date, selectedTime)
                    // You can use localDateTime here for further processing
                    Log.d("SelectedDateTime", "Combined: $localDateTime")

                    // Optionally, assign this LocalDateTime to another variable
                    // selectedDateTime = localDateTime
                }

                showTimePicker = false
            },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        ).show()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {
                    selectedDate?.let { date ->
                        selectedTime?.let { time ->
                            selectedDateTime = LocalDateTime.of(date, time) // Combine date and time
                        }
                    }
                    selectedDateTime?.let {
                        val formattedDate =
                            it.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")) // Format date
                        val formattedTime =
                            it.format(DateTimeFormatter.ofPattern("HH:mm")) // Format time

                        Text(
                            text = "Selected: $formattedDate at $formattedTime"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { eventDispatcher.invoke(NewTaskContract.Intent.OnBackClick) },
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
                    IconButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_time_picker),
                            contentDescription = null
                        )
                    }
                    Button(
                        onClick = {
                            val todoData = selectedDateTime?.let { date ->
                                val formattedDate =
                                    date.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")) // Format date
                                val formattedTime =
                                    date.format(DateTimeFormatter.ofPattern("HH:mm")) // Format time

                                TodoUIData(
                                    id = 0,
                                    task = task,
                                    taskType = selectedTaskType,
                                    hasSubTask = hasSubTask,
                                    subList = if (hasSubTask) subTaskList
                                        .filter { data -> data.second.isNotBlank() || data.second.isNotEmpty() }
                                        .map { subtask ->
                                            SubTaskEntity(
                                                id = 0,
                                                checked = subtask.first,
                                                subTask = subtask.second,
                                                todoId = 0
                                            )
                                        } else emptyList(),
                                    time = "$formattedDate $formattedTime",
                                    checked = false
                                )

                            } // Format in hh:mm AM/PM
                            todoData?.let {
                                Log.d("nnn", "NewTaskScreenContent: ${it.subList}")
                                saveTaskAndScheduleAlarm(context, it)

                            }

                            eventDispatcher.invoke(
                                NewTaskContract.Intent.OnSaveClick(
                                    todoData!!
                                )
                            )
                        },
                        enabled = task.isNotEmpty() && selectedTime != null,
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

@Composable
fun SubTaskField(
    data: Pair<Boolean, String>,
    onTextChange: (String) -> Unit,
    onCheckChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)

    ) {

        Checkbox(
            checked = data.first,
            onCheckedChange = onCheckChange
        )
        TextField(
            modifier = Modifier,
            value = data.second,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    text = "Write subtask",
                    fontSize = 17.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
        )
    }
}


@Preview
@Composable
fun NewTaskScreenPreview() {
    NewTaskScreenContent()
}