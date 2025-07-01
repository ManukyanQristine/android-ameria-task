package com.task.presentation.ui.userslist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.task.presentation.R
import com.task.presentation.ui.components.AppSearchBar
import com.task.presentation.ui.components.AppTopBar
import com.task.presentation.ui.theme.DividerGray
import com.task.presentation.ui.theme.LightGray
import com.task.presentation.ui.theme.Purple
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UsersListScreen(
    viewModel: UsersListViewModel,
    onUserClick: (UserUiModel) -> Unit
) {
    val users = viewModel.users.collectAsLazyPagingItems()
    val isConnected by viewModel.isConnected.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val noConnectionMessage = stringResource(R.string.no_internet_connection)

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

    LaunchedEffect(users.loadState.refresh) {
        if (users.loadState.refresh is LoadState.Error) {
            val error = (users.loadState.refresh as LoadState.Error).error
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = error.message.toString(), duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Column(
                modifier = Modifier
                    .background(Purple)
            ) {
                AppTopBar(title = stringResource(R.string.users_list_screen_title))
                AppSearchBar(
                    query = viewModel.searchQuery.collectAsState().value,
                    onQueryChange = { viewModel.searchQuery.value = it }
                )
            }
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGray),
            contentPadding = paddingValues,
        ) {
            items(users.itemCount) { index ->
                val user = users[index]
                user?.let { UserListItem(user = it, onClick = { onUserClick(it) }) }
                if (index < users.itemCount - 1) {
                    HorizontalDivider(
                        thickness = dimensionResource(R.dimen.divider_thickness),
                        color = DividerGray
                    )
                }
            }

            // Load more indicator
            when (val state = users.loadState.append) {
                is LoadState.Loading -> item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
                    }
                }

                is LoadState.Error -> item {
                    val errorMessage =
                        stringResource(R.string.error_loading_more_users, state.error.message ?: "")
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = errorMessage, duration = SnackbarDuration.Short
                        )
                    }
                }

                else -> {}
            }

            // Initial loading
            if (users.loadState.refresh is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun UsersListScreenPreview() {
    UsersListScreen(
        viewModel = hiltViewModel()
    ) {}
}
