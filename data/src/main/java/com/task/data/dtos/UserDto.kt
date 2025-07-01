package com.task.data.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val login: String,
    val id: Long,
    @Json(name = "avatar_url") val avatarUrl: String
)
