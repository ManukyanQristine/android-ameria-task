package com.task.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.task.data.api.GitHubApi
import com.task.data.dtos.UserDto

class UsersPagingSource(
    private val apiService: GitHubApi
) : PagingSource<Long, UserDto>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, UserDto> {
        return try {
            val since = params.key ?: 0
            val users = apiService.getUsers(since = since.toInt(), perPage = params.loadSize)
            val nextKey = if (users.isEmpty()) null else users.last().id
            LoadResult.Page(
                data = users,
                prevKey = null, // GitHub API doesn't support backward pagination
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, UserDto>): Long? {
        // Reset to initial since=0 when refresh is triggered
        return null
    }
}