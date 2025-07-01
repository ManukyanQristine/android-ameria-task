package com.task.presentation.ui.userdetails

data class UserDetailsUiModel(
    val username: String,
    val avatarUrl: String,
    val location: String?,
    val followers: Int,
    val following: Int,
    val bio: String?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val updatedAt: String?
)