package com.task.data.api

import com.task.data.dtos.UserDetailDto
import com.task.data.dtos.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): UserDetailDto
}
