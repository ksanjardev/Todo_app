package uz.sanjar.androidweekexam33.utils.navigator

/**   Created by Sanjar Karimov 12:47 AM 12/26/2024   */

interface AppNavigator {
    suspend fun navigateTo(screen: AppScreen)
    suspend fun navigateToSingleTop(screen: AppScreen)
    suspend fun navigateTo(screens: List<AppScreen>)

    suspend fun replace(screen: AppScreen)
    suspend fun replaceAll(screen: AppScreen)
    suspend fun replaceAll(screen: List<AppScreen>)

    suspend fun back()
    suspend fun backAll()

    suspend fun <T : AppScreen> backUntil(clazz: Class<T>)
    suspend fun backUntil(predicate: (AppScreen) -> Boolean): Boolean
}