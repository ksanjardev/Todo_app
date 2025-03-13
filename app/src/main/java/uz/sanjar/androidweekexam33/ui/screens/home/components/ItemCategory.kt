package uz.sanjar.androidweekexam33.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.sanjar.androidweekexam33.utils.task_type.TaskType

/**   Created by Sanjar Karimov 12:48 PM 1/19/2025   */

data class CategoryItem(
    val categoryName: String,
    val taskCount: Int,
    val taskType: TaskType,
)

@Composable
fun ItemCategory(data: CategoryItem) {
    Box(
        modifier = Modifier
            .width(189.dp)
            .height(93.dp)
            .background(color = data.taskType.backgroundColor, shape = RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(data.taskType.iconRes),
                contentDescription = null,
                tint = data.taskType.color
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    text = "${data.taskCount}",
                    fontSize = 17.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = data.categoryName,
                    fontSize = 17.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}