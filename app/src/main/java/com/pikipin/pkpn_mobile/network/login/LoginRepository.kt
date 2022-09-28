package com.pikipin.pkpn_mobile.network.login
import android.content.Context
import com.pikipin.pkpn_mobile.di.RepositoryEntryPoint
import com.pikipin.pkpn_mobile.di.util.getEntryPoint

interface ILoginRepository {
    suspend fun registerUser(loginRequestBody: LoginBody): String
}

class LoginRepository(val context: Context) : ILoginRepository {

    private val loginService = context.getEntryPoint<RepositoryEntryPoint>().loginService()

    override suspend fun registerUser(loginRequestBody: LoginBody): String {
        return loginService.getVerificationCode(loginRequestBody)
    }
}



