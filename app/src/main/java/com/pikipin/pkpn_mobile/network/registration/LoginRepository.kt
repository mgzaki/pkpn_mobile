package com.pikipin.pkpn_mobile.network.login
import android.content.Context
import com.pikipin.pkpn_mobile.di.RepositoryEntryPoint
import com.pikipin.pkpn_mobile.di.util.getEntryPoint


interface ILoginRepository {
    suspend fun registerUser(loginRequestBody: RegisterBody): LoginResponse
    suspend fun verifyCode(code: String, request: VerifyBody): VerifyResponse

}

class LoginRepository(val context: Context) : ILoginRepository {

    private val loginService = context.getEntryPoint<RepositoryEntryPoint>().loginService()

    override suspend fun registerUser(loginRequestBody: RegisterBody): LoginResponse {
        return loginService.getVerificationCode(loginRequestBody)
    }

    override suspend fun verifyCode(code: String, request: VerifyBody): VerifyResponse {
        return loginService.verifyCode(code, request)
    }
}



