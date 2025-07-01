package com.task.presentation.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.domain.network.NetworkMonitor
import com.task.domain.usecases.GetUserDetailsUseCase
import com.task.presentation.ui.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val uiState: StateFlow<UserDetailsUiState> = _uiState

    private val _username = MutableStateFlow<String?>(null)
    val isConnected = networkMonitor.isConnected()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    init {
        viewModelScope.launch {
            isConnected.collectLatest { connected ->
                val username = _username.value
                if (connected && username != null && uiState.value !is UserDetailsUiState.Success) {
                    fetchUser(username)
                }
            }
        }
    }

    fun loadUser(username: String) {
        _username.value = username
        fetchIfConnected(username)
    }

    private fun fetchIfConnected(username: String) {
        viewModelScope.launch {
            if (isConnected.value)
                fetchUser(username)
            else
                _uiState.value = UserDetailsUiState.Loading
        }
    }

    private suspend fun fetchUser(username: String) {
        _uiState.value = UserDetailsUiState.Loading
        getUserDetailsUseCase(username)
            .catch { e ->
                _uiState.value = UserDetailsUiState.Error(e.message ?: "Unknown error")
            }
            .collectLatest { user ->
                _uiState.value = UserDetailsUiState.Success(user.toUiModel())
            }
    }
}
