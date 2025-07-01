package com.task.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.task.presentation.ui.navigation.AppNavGraph
import com.task.presentation.ui.theme.GitHubUsersTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUsersTaskTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}