package com.task.presentation.ui.mappers

import com.task.domain.entities.User
import com.task.domain.entities.UserDetails
import com.task.presentation.ui.userdetails.UserDetailsUiModel
import com.task.presentation.ui.userslist.UserUiModel

fun UserDetails.toUiModel(): UserDetailsUiModel {
    return UserDetailsUiModel(
        username = login,
        avatarUrl = avatarUrl,
        location = location,
        followers = followers,
        following = following,
        bio = bio,
        publicRepos = publicRepos,
        publicGists = publicGists,
        updatedAt = updatedAt
    )
}

fun User.toUiModel(): UserUiModel {
    return UserUiModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        score = "1.0" // Assuming score is not available in User entity, set a default value
    )
}