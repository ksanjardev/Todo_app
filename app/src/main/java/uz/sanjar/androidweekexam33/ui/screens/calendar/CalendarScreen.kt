package uz.sanjar.androidweekexam33.ui.screens.calendar

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.sanjar.androidweekexam33.ui.screens.calendar.components.ItemDate
import uz.sanjar.androidweekexam33.ui.screens.calendar.components.ItemTask
import uz.sanjar.androidweekexam33.ui.screens.calendar.components.TopBarText
import uz.sanjar.androidweekexam33.ui.screens.calendar.components.getCurrentWeekDates
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**   Created by Sanjar Karimov 10:59 AM 1/19/2025   */


class CalendarScreen : Screen {
    @SuppressLint("NewApi")
    @Composable
    override fun Content() {
        val viewModel: CalendarContract.ViewModel = getViewModel<CalendarViewModel>()

        CalendarScreenContent(
            viewModel::eventDispatcher,
            viewModel.uiState.collectAsState()
        )

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreenContent(
    eventDispatcher: (CalendarContract.Intent) -> Unit = {},
    uiState: State<CalendarContract.UIState> = remember { mutableStateOf(CalendarContract.UIState()) },
) {
    var selectedDateIndex by remember { mutableIntStateOf(0) }
    val currentWeekDates = getCurrentWeekDates()
    val selectedDateItem = currentWeekDates[selectedDateIndex]

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    TopBarText(
                        "Calendar",
                        "${selectedDateItem.date} ${getCurrentMonthName()}"
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 32.dp)
                    .padding(horizontal = 22.dp)

            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        LazyRow {
                            itemsIndexed(currentWeekDates) { index, data ->
                                ItemDate(
                                    data = data,
                                    isSelected = index == selectedDateIndex,
                                    onClick = {
                                        selectedDateIndex = index
                                        eventDispatcher.invoke(
                                            CalendarContract.Intent.OnDateSelected(data.day)
                                        )
                                    }
                                )
                                Spacer(Modifier.width(10.dp))
                            }
                        }
                    }
                    items(24) { hour ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = String.format("%02d:00", hour),
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                                uiState.value.todoList.find {
                                    extractHourAndMinute(it.time).first.toInt() == hour && extractHourAndMinute(
                                        it.time
                                    ).second.toInt() == 0
                                }?.let { task ->
                                    ItemTask(task)
                                }
                            }
                            val list =
                                uiState.value.todoList.filter {
                                    extractHourAndMinute(it.time).first.toInt() == hour
                                            && extractHourAndMinute(
                                        it.time
                                    ).second.toInt() != 0
                                }
                                    .sortedBy {
                                        extractHourAndMinute(
                                            it.time
                                        ).second.toInt()
                                    }

                            list.forEach { _ ->

                                uiState.value.todoList.find { extractHourAndMinute(it.time).first.toInt() == hour }
                                    ?.let { task ->
                                        ItemTask(task)
                                    }
                            }
                        }

                    }
                }
            }
        }
    )
}

fun extractHourAndMinute(datetime: String): Pair<String, String> {
    var hour = ""
    var minute = ""
    for (i in datetime.length - 1 downTo 0) {
        if (datetime[i] == ':') {

            hour = datetime[i - 2].toString() + datetime[i - 1].toString()
            minute =
                datetime[i + 1].toString() + datetime[i + 2].toString()
            break
        }
    }

    return Pair(hour, minute)
}


@SuppressLint("NewApi")
fun getCurrentMonthName(): String {
    val currentMonth = LocalDate.now().month
    return currentMonth.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CalendarScreenPreview() {
    CalendarScreenContent()
}