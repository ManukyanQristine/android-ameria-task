package com.task.domain.entities

data class UserDetails(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val location: String?,
    val followers: Int,
    val following: Int,
    val bio: String?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val updatedAt: String?
)