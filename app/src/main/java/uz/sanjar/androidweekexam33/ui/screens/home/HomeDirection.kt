package uz.sanjar.androidweekexam33.ui.screens.home

import uz.sanjar.androidweekexam33.domain.TodoUIData
import uz.sanjar.androidweekexam33.ui.screens.calendar.CalendarScreen
import uz.sanjar.androidweekexam33.ui.screens.new_task.NewTaskScreen
import uz.sanjar.androidweekexam33.ui.screens.view_task.EditScreen
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigator
import javax.inject.Inject

/**   Created by Sanjar Karimov 1:58 PM 1/19/2025   */

class HomeDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : HomeContract.Direction{
    override suspend fun openNewTaskScreen() {
        appNavigator.navigateTo(NewTaskScreen())
    }

    override suspend fun openCalendar() {
        appNavigator.navigateTo(CalendarScreen())
    }

    override suspend fun openEditScreen(todoUIData: TodoUIData) {
        appNavigator.navigateTo(EditScreen(todoUIData))
    }
}