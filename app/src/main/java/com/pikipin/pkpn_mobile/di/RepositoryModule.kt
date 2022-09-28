package com.pikipin.pkpn_mobile.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.pikipin.pkpn_mobile.network.BaseUrls.Companion.LOGIN_BASE_URL
import com.pikipin.pkpn_mobile.network.login.ILoginRepository
import com.pikipin.pkpn_mobile.network.login.LoginRepository
import com.pikipin.pkpn_mobile.network.login.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(@ApplicationContext context: Context): ILoginRepository {
        return LoginRepository(context)
    }

    @Provides
    @Singleton
    fun provideLoginService(): LoginService {
        return Retrofit.Builder()
            .baseUrl(URL(LOGIN_BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(LoginService::class.java)
    }
}