package com.task.data.localstorage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserDetailsEntity(
    @PrimaryKey val id: Long,
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

