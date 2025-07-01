package com.task.presentation.ui.userdetails

sealed class UserDetailsUiState {
    data object Loading : UserDetailsUiState()
    data class Error(val message: String) : UserDetailsUiState()
    data class Success(val user: UserDetailsUiModel) : UserDetailsUiState()
}