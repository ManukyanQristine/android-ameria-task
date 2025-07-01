package com.task.presentation.ui.navigation

sealed class Screen(val route: String) {
    data object UsersList : Screen("users_list")
    data object UserDetails : Screen("user_details/{username}") {
        const val ARG_USERNAME = "username"
        fun createRoute(username: String) = "user_details/$username"
    }
}
