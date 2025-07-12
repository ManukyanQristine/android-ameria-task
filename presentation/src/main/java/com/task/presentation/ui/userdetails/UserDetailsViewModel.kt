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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the UI state for the user details screen.
 */
sealed class UserDetailsUiState {
    object Loading : UserDetailsUiState()
    object NoConnection : UserDetailsUiState()
    data class Error(val message: String) : UserDetailsUiState()
    data class Success(val user: UserDetailsUiModel) : UserDetailsUiState()
}

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    /**
     * UI state exposed to the UI layer.
     */
    val uiState: StateFlow<UserDetailsUiState> = _uiState

    /**
     * Network connectivity state.
     */
    val isConnected = networkMonitor.isConnected()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    /**
     * Loads user details for the given username. Handles connection and error states.
     */
    fun loadUser(username: String) {
        viewModelScope.launch {
            if (isConnected.value) {
                _uiState.value = UserDetailsUiState.Loading
                getUserDetailsUseCase(username)
                    .catch { e ->
                        _uiState.value = UserDetailsUiState.Error(e.message ?: "Unknown error")
                    }
                    .collect { user ->
                        _uiState.value = UserDetailsUiState.Success(user.toUiModel())
                    }
            } else {
                _uiState.value = UserDetailsUiState.NoConnection
            }
        }
    }
}
