package com.task.presentation.ui.userdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.presentation.R
import com.task.presentation.ui.components.AppTopBar
import com.task.presentation.ui.theme.LightGray
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UserDetailsScreen(
    username: String,
    viewModel: UserDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isConnected by viewModel.isConnected.collectAsState()
    val noConnectionMessage = stringResource(R.string.no_internet_connection)

    LaunchedEffect(username) {
        viewModel.loadUser(username)
    }

    // Show Snackbar when disconnected
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = noConnectionMessage, duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.user_details_screen_title),
                onBackClick = onBackClick,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(LightGray),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is UserDetailsUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                is UserDetailsUiState.Error -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = state.message, duration = SnackbarDuration.Short
                        )
                    }
                }

                is UserDetailsUiState.Success -> UserDetailsContent(user = state.user)
            }
        }
    }
}

@Composable
@Preview
fun UserDetailsScreenPreview() {
    UserDetailsScreen(
        username = "john_doe",
        onBackClick = {}
    )
}
