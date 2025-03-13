package uz.sanjar.androidweekexam33.broadcast_receiver

/**   Created by Sanjar Karimov 2:34 PM 1/20/2025   */

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import uz.sanjar.androidweekexam33.R
import uz.sanjar.androidweekexam33.data.local.entity.TodoEntity
import uz.sanjar.androidweekexam33.domain.TodoUIData
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: "Task Reminder"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "TASK_REMINDER_CHANNEL"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText("it is time to do $taskTitle")
            .setSmallIcon(R.drawable.img)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}



@SuppressLint("NewApi", "ScheduleExactAlarm")
fun saveTaskAndScheduleAlarm(context: Context, todoEntity: TodoUIData) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, TaskAlarmReceiver::class.java).apply {
        putExtra("TASK_TITLE", todoEntity.task)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        todoEntity.id.toInt(), // Ensure this is unique for each task
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Parse the full date and time from the todoEntity.time string
    val taskDateTime = LocalDateTime.parse(todoEntity.time, DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm"))
    val currentDateTime = LocalDateTime.now()

    // If the task time is earlier than the current time, schedule it for the next day
    var alarmDateTime = taskDateTime
    if (taskDateTime.isBefore(currentDateTime)) {
        alarmDateTime = taskDateTime.plusDays(1)
    }

    // Convert LocalDateTime to milliseconds
    val alarmTimeMillis = alarmDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    // Set the alarm
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        alarmTimeMillis,
        pendingIntent
    )
}
