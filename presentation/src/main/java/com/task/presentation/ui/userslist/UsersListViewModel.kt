package com.task.presentation.ui.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.task.domain.network.NetworkMonitor
import com.task.domain.usecases.GetUsersUseCase
import com.task.presentation.ui.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Represents the UI state for the users list screen.
 */
sealed class UsersListUiState {
    object Idle : UsersListUiState()
    object Loading : UsersListUiState()
    data class Error(val message: String) : UsersListUiState()
}

@HiltViewModel
class UsersListViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    /**
     * Network connectivity state.
     */
    val isConnected: StateFlow<Boolean> = networkMonitor.isConnected()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            true
        )

    /**
     * Search query for filtering users.
     */
    val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow<UsersListUiState>(UsersListUiState.Idle)
    /**
     * UI state exposed to the UI layer.
     */
    val uiState: StateFlow<UsersListUiState> = _uiState

    private val baseUsers = getUsersUseCase()
        .cachedIn(viewModelScope)

    /**
     * Users flow for paging, filtered by search query.
     */
    @OptIn(FlowPreview::class)
    val users = searchQuery
        .debounce(300)
        .combine(baseUsers) { query, pagingData ->
            if (query.isBlank()) {
                pagingData
            } else {
                pagingData.filter { user ->
                    user.login.contains(query, ignoreCase = true) ||
                            user.id.toString().contains(query)
                }
            }
        }
        .map { filtered ->
            filtered.map { it.toUiModel() }
        }
        .catch { e ->
            _uiState.value = UsersListUiState.Error(e.message ?: "Unknown error")
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}