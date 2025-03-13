package uz.sanjar.androidweekexam33.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.sanjar.androidweekexam33.data.local.TodoDatabase
import uz.sanjar.androidweekexam33.data.local.dao.SubTaskDao
import uz.sanjar.androidweekexam33.data.local.dao.TodoDao
import javax.inject.Singleton

/**   Created by Sanjar Karimov 4:22 PM 1/19/2025   */

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @[Provides Singleton]
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, "TodoDatabase").build()


    @[Provides Singleton]
    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()

    @[Provides Singleton]
    fun provideSubTaskDao(database: TodoDatabase): SubTaskDao = database.subtaskDao()
}