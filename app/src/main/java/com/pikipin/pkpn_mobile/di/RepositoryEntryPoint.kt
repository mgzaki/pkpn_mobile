package com.pikipin.pkpn_mobile.di

import com.pikipin.pkpn_mobile.network.registration.LoginService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepositoryEntryPoint {
    fun loginService(): LoginService
}