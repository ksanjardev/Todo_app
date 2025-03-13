package uz.sanjar.androidweekexam33.ui.screens.calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**   Created by Sanjar Karimov 11:14 AM 1/19/2025   */

data class DateItem(
    val day: String,
    val date: String,
)

@Composable
fun ItemDate(data: DateItem, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(74.dp)
            .background(color = if (isSelected) Color(0xFFF6F6F6) else Color.White)
            .border(
                width = 1.dp, color = if (isSelected) Color(0xFF121212) else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = data.day,
                fontSize = 12.sp,
                color = if (isSelected) Color(0xFF121212) else Color.LightGray
            )
            Text(
                text = data.date,
                fontSize = 18.sp,
                color = if (isSelected) Color(0xFF121212) else Color.LightGray
            )
        }
    }
}


// Generate the current week dates
@SuppressLint("NewApi")
fun getCurrentWeekDates(): List<DateItem> {
    val today = LocalDate.now()
    return (0..6).map { dayOffset ->
        val date = today.plusDays(dayOffset.toLong())
        val day = date.format(DateTimeFormatter.ofPattern("EEE")) // Day: Mon, Tue, etc.
        val dateNumber = date.dayOfMonth.toString() // Date: 19, 20, etc.
        DateItem(day = day, date = dateNumber)
    }
}
