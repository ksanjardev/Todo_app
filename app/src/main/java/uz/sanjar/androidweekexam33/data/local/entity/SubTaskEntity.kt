package uz.sanjar.androidweekexam33.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/**   Created by Sanjar Karimov 4:07 PM 1/19/2025   */

@Entity(

)
data class SubTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var todoId: Long = 0,
    val checked: Boolean = false,
    val subTask: String,
)
