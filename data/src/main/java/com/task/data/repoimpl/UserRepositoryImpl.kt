package com.task.data.repoimpl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.task.data.api.GitHubApi
import com.task.data.localstorage.UserDao
import com.task.data.mappers.toDomain
import com.task.data.mappers.toEntity
import com.task.data.paging.UsersPagingSource
import com.task.domain.entities.User
import com.task.domain.entities.UserDetails
import com.task.domain.network.NetworkMonitor
import com.task.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GitHubApi,
    private val dao: UserDao,
    private val networkMonitor: NetworkMonitor
) : UserRepository {

    override fun getUsersPaged(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                UsersPagingSource(api)
            }
        ).flow.map { pagingData ->
            pagingData.map { dto -> dto.toDomain() }
        }
    }

    override fun observeUserDetails(username: String): Flow<UserDetails> = flow {
        emitAll(
            dao.observeUserByLogin(username)
                .filterNotNull()
                .map { it.toDomain() }
        )
    }.onStart {
        if (networkMonitor.isConnected().first()) {
            runCatching {
                val remote = api.getUserDetails(username)
                dao.insert(remote.toEntity())
            }.onFailure {
                Log.e("UserRepositoryImpl", "Failed to fetch user details", it)
            }
        } else {
            Log.w("UserRepositoryImpl", "No internet connection, using cached data")
        }
    }.flowOn(Dispatchers.IO)


}
