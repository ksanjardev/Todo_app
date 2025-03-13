package uz.sanjar.androidweekexam33.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigator
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigatorDispatcher
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigatorHandler

/**   Created by Sanjar Karimov 12:55 AM 12/26/2024   */

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl: AppNavigatorDispatcher): AppNavigator

    @Binds
    fun bindAppNavigatorHandler(impl: AppNavigatorDispatcher): AppNavigatorHandler

}