package com.task.domain.usecases

import com.task.domain.entities.UserDetails
import com.task.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserDetailsUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(username: String): Flow<UserDetails> {
        return repository.observeUserDetails(username)
    }
}