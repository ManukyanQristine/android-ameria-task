package com.task.domain.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun isConnected(): Flow<Boolean>
}