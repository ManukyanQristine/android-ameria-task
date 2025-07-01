package com.task.data.mappers

import com.task.data.dtos.UserDetailDto
import com.task.data.dtos.UserDto
import com.task.data.localstorage.UserDetailsEntity
import com.task.domain.entities.User
import com.task.domain.entities.UserDetails

fun UserDto.toDomain() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl
)

fun UserDetailDto.toEntity() = UserDetailsEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    location = location,
    followers = followers,
    following = following,
    bio = bio,
    publicRepos = publicRepos,
    publicGists = publicGists,
    updatedAt = updatedAt
)

fun UserDetailsEntity.toDomain() = UserDetails(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    location = location,
    followers = followers,
    following = following,
    bio = bio,
    publicRepos = publicRepos,
    publicGists = publicGists,
    updatedAt = updatedAt
)