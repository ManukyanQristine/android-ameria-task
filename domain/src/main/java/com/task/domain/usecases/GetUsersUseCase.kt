package com.task.domain.usecases

import androidx.paging.PagingData
import com.task.domain.entities.User
import com.task.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(
    ): Flow<PagingData<User>> = repository.getUsersPaged()
}