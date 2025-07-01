package com.task.domain.di

import com.task.domain.repositories.UserRepository
import com.task.domain.usecases.GetUserDetailsUseCase
import com.task.domain.usecases.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase =
        GetUsersUseCase(repository)

    @Provides
    fun provideGetUserDetailUseCase(repository: UserRepository): GetUserDetailsUseCase =
        GetUserDetailsUseCase(repository)
}