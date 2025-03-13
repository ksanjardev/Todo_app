package uz.sanjar.androidweekexam33.ui.screens.home.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import uz.sanjar.androidweekexam33.data.local.entity.SubTaskEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 1:16 PM 1/19/2025   */


@OptIn(ExperimentalWearMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemTask(
    taskData: TodoUIData,
    onClick: (TodoUIData) -> Unit,
    time: String,
    onSubtaskClick: (SubTaskEntity) -> Unit = {},
    onItemClick: (TodoUIData) -> Unit,
) {
    var todoClicked by remember { mutableStateOf(taskData.checked) }



    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Log.d("LLL", "incoming: $taskData")
                    onItemClick(
                        TodoUIData(
                            id = taskData.id,
                            task = taskData.task,
                            taskType = taskData.taskType,
                            time = taskData.time,
                            hasSubTask = taskData.hasSubTask,
                            checked = todoClicked,
                            subList = taskData.subList
                        )
                    )
                },
        ) {
            Checkbox(
                checked = todoClicked,
                onCheckedChange = {
                    // root

                    todoClicked = it
                    onClick(taskData.copy(checked = todoClicked))
                },
                colors = CheckboxDefaults.colors(checkedColor = taskData.taskType.color)
            )
            Spacer(Modifier.width(10.dp))

            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = taskData.task,
                    textDecoration = if (todoClicked) TextDecoration.LineThrough else TextDecoration.None,
                    fontSize = 17.sp,
                    color = Color.Black
                )
                Spacer(Modifier.height(2.dp))
                Row {
                    Text(
                        text = taskData.taskType.taskName,
                        fontSize = 12.sp,
                        color = taskData.taskType.color,
                        modifier = Modifier
                            .background(
                                color = taskData.taskType.backgroundColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp, vertical = 3.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = time.takeLast(5),
                        fontSize = 12.sp,
                        color = taskData.taskType.color,
                        modifier = Modifier
                            .background(
                                color = taskData.taskType.backgroundColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp, vertical = 3.dp)

                    )
                }
                Spacer(Modifier.height(10.dp))
                if (taskData.hasSubTask) {
                    Spacer(Modifier.height(10.dp))
                    Column {
                        taskData.subList.forEachIndexed { _, data ->
                            var subClicked by remember { mutableStateOf(data.checked) }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemClick(taskData) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = subClicked,
                                    onCheckedChange = {
                                        // child
                                        subClicked = it
                                        onSubtaskClick(data.copy(checked = subClicked))
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = taskData.taskType.color)
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = data.subTask,
                                    textDecoration = if (subClicked) TextDecoration.LineThrough else TextDecoration.None,
                                    fontSize = 17.sp,
                                    color = Color.Black
                                )
                                Spacer(Modifier.height(5.dp))
                            }
                        }
                    }
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }

}



