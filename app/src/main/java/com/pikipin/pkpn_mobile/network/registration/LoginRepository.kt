package com.pikipin.pkpn_mobile.network.registration
import android.content.Context
import com.pikipin.pkpn_mobile.di.RepositoryEntryPoint
import com.pikipin.pkpn_mobile.di.util.getEntryPoint
import com.pikipin.pkpn_mobile.network.registration.login.LoginBody
import com.pikipin.pkpn_mobile.network.registration.login.LoginResponse
import com.pikipin.pkpn_mobile.network.registration.verify.SendVerificationCodeResponse
import com.pikipin.pkpn_mobile.network.registration.verify.VerifyBody
import com.pikipin.pkpn_mobile.network.registration.verify.VerifyCodeResponse


interface ILoginRepository {
    suspend fun registerUser(loginRequestBody: RegisterBody): SendVerificationCodeResponse
    suspend fun verifyCode(code: String, request: VerifyBody): VerifyCodeResponse
    suspend fun login(username: String, password:String): LoginResponse
}

class LoginRepository(val context: Context) : ILoginRepository {

    private val loginService = context.getEntryPoint<RepositoryEntryPoint>().loginService()

    override suspend fun registerUser(loginRequestBody: RegisterBody): SendVerificationCodeResponse {
        return loginService.sendVerificationCode(loginRequestBody)
    }

    override suspend fun verifyCode(code: String, request: VerifyBody): VerifyCodeResponse {
        return loginService.verifyCode(code, request)
    }

    override suspend fun login(username: String, password: String): LoginResponse {
        return loginService.login(LoginBody(username, password))
    }
}



