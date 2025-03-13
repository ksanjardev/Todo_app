package uz.sanjar.androidweekexam33.utils.navigator

import kotlinx.coroutines.flow.Flow
import uz.sanjar.androidweekexam33.utils.navigator.NavigationArgs

/**   Created by Sanjar Karimov 12:45 AM 12/26/2024   */

interface AppNavigatorHandler {
    val navigation: Flow<NavigationArgs>
}