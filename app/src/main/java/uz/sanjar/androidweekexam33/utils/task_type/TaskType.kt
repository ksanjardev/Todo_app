package uz.sanjar.androidweekexam33.utils.task_type

import androidx.compose.ui.graphics.Color
import uz.sanjar.androidweekexam33.R

/**   Created by Sanjar Karimov 12:06 PM 1/19/2025   */
enum class TaskType(
    val color: Color, val backgroundColor: Color,
    val iconRes: Int,
    val taskName: String
) {
    HEALTH(
        Color(0xFF7990F8),
        Color(0x1A7990F8),
        R.drawable.ic_health,
        "HEALTH"
    ),
    WORK(
        Color(0xFF46CF8B),
        Color(0x1A46CF8B),
        R.drawable.ic_work,
        "WORK"
    ),
    MENTAL_HEALTH(
        Color(0xFFBC5EAD),
        Color(0x1ABC5EAD),
        R.drawable.ic_mental_health,
        "MENTAL HEALTH"
    ),
    OTHERS(
        Color(0xFF908986),
        Color(0x1A908986),
        R.drawable.ic_other,
        "OTHER"
    )
}