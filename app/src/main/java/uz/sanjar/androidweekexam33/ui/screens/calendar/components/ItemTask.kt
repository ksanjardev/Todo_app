package uz.sanjar.androidweekexam33.ui.screens.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.sanjar.androidweekexam33.domain.TodoUIData

/**   Created by Sanjar Karimov 12:05 PM 1/19/2025   */


@Composable
fun ItemTask(data: TodoUIData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (data.time.takeLast(2) != "00")
            Text(
                text = data.time.takeLast(5),
                fontSize = 14.sp,
                color = Color.Black
            )
        if (data.task.isNotEmpty()) {
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .width(330.dp)
                    .height(72.dp)
                    .background(
                        color = data.taskType.backgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp)
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(color = data.taskType.color, shape = CircleShape)
                    )
                    Spacer(Modifier.width(7.dp))
                    Text(
                        text = data.task,
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
    }
}
