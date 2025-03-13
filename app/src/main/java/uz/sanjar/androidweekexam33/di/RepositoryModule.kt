package uz.sanjar.androidweekexam33.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.sanjar.androidweekexam33.data.TodoRepository
import uz.sanjar.androidweekexam33.data.TodoRepositoryImpl
import javax.inject.Singleton

/**   Created by Sanjar Karimov 4:25 PM 1/19/2025   */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindTodoRepository(impl: TodoRepositoryImpl): TodoRepository

}