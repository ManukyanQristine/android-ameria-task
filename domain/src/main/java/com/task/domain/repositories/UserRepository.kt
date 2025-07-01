package com.task.domain.repositories

import androidx.paging.PagingData
import com.task.domain.entities.User
import com.task.domain.entities.UserDetails
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun getUsersPaged(): Flow<PagingData<User>>
    fun observeUserDetails(username: String): Flow<UserDetails>
}