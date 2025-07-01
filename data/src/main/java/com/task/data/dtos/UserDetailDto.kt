package com.task.data.dtos

import com.squareup.moshi.Json

data class UserDetailDto(
    val login: String,
    val id: Long,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val name: String?,
    val location: String?,
    val followers: Int,
    val following: Int,
    val bio: String?,
    @Json(name = "public_repos")
    val publicRepos: Int?,
    @Json(name = "public_gists")
    val publicGists: Int?,
    @Json(name = "updated_at")
    val updatedAt: String?
)
