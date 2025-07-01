package com.task.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.task.presentation.ui.navigation.Screen.UserDetails.ARG_USERNAME
import com.task.presentation.ui.userdetails.UserDetailsScreen
import com.task.presentation.ui.userslist.UsersListScreen
import com.task.presentation.ui.userslist.UsersListViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.UsersList.route
    ) {
        composable(Screen.UsersList.route) {
            UsersListScreen(
                viewModel = hiltViewModel<UsersListViewModel>(),
                onUserClick = { user ->
                    navController.navigate(Screen.UserDetails.createRoute(user.login))
                }
            )
        }

        composable(
            route = Screen.UserDetails.route,
            arguments = listOf(navArgument(ARG_USERNAME) { type = NavType.StringType })

        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString(ARG_USERNAME)
            username?.let {
                UserDetailsScreen(username = it) {
                    navController.popBackStack()
                }
            }
        }
    }
}