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
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    val isConnected: StateFlow<Boolean> = networkMonitor.isConnected()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            true
        )

    private val baseUsers = getUsersUseCase()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val users = searchQuery
        .debounce(300)
        .combine(baseUsers) { query, pagingData ->
            val filtered = if (query.isBlank()) {
                pagingData
            } else {
                pagingData.filter { user ->
                    user.login.contains(query, ignoreCase = true)
                            || user.id.toString().contains(query)
                }
            }
            filtered.map { it.toUiModel() }

        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}