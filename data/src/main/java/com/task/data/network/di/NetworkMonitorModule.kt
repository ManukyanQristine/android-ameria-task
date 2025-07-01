package com.task.data.network.di

import com.task.data.network.NetworkMonitorImpl
import com.task.domain.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkMonitorModule {

    @Binds
    fun bindNetworkMonitor(impl: NetworkMonitorImpl): NetworkMonitor
}