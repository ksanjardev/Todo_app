package uz.sanjar.androidweekexam33.ui.screens.view_task

import uz.sanjar.androidweekexam33.ui.screens.home.HomeScreen
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigator
import javax.inject.Inject

/**   Created by Sanjar Karimov 6:58 PM 1/20/2025   */

class EditDirection @Inject constructor(
    private val appNavigator: AppNavigator,
) : EditContract.Direction {
    override suspend fun openHome() {
        appNavigator.replace(HomeScreen())
    }

    override suspend fun back() {
        appNavigator.back()
    }

}