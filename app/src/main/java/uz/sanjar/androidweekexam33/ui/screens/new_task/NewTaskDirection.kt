package uz.sanjar.androidweekexam33.ui.screens.new_task

import uz.sanjar.androidweekexam33.ui.screens.home.HomeScreen
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigator
import javax.inject.Inject

/**   Created by Sanjar Karimov 1:58 PM 1/19/2025   */

class NewTaskDirection @Inject constructor(
    private val appNavigator: AppNavigator,
) : NewTaskContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun openHome() {
        appNavigator.replace(HomeScreen())
    }
}