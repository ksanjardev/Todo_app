package uz.sanjar.androidweekexam33.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.sanjar.androidweekexam33.ui.screens.home.HomeContract
import uz.sanjar.androidweekexam33.ui.screens.home.HomeDirection
import uz.sanjar.androidweekexam33.ui.screens.new_task.NewTaskContract
import uz.sanjar.androidweekexam33.ui.screens.new_task.NewTaskDirection
import uz.sanjar.androidweekexam33.ui.screens.view_task.EditContract
import uz.sanjar.androidweekexam33.ui.screens.view_task.EditDirection

/**   Created by Sanjar Karimov 1:59 PM 1/19/2025   */

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @Binds
    fun bindHomeDirection(impl: HomeDirection): HomeContract.Direction


    @Binds
    fun bindNewTaskDirection(impl: NewTaskDirection): NewTaskContract.Direction

    @Binds
    fun bindEditDirection(impl: EditDirection): EditContract.Direction

}