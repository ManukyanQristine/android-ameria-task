package com.task.data.network

import com.task.domain.network.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkMonitorImpl @Inject constructor(
    private val tracker: NetworkStatusTracker
) : NetworkMonitor {
    override fun isConnected(): Flow<Boolean> = tracker.networkStatus
}