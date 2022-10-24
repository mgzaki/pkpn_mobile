package com.pikipin.pkpn_mobile.network.registration

import com.pikipin.pkpn_mobile.network.registration.login.LoginBody
import com.pikipin.pkpn_mobile.network.registration.login.LoginResponse
import com.pikipin.pkpn_mobile.network.registration.verify.SendVerificationCodeResponse
import com.pikipin.pkpn_mobile.network.registration.verify.VerifyBody
import com.pikipin.pkpn_mobile.network.registration.verify.VerifyCodeResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {

    @POST("/prod/register")
    suspend fun sendVerificationCode(@Body request: RegisterBody): SendVerificationCodeResponse

    @POST("/prod/verify/{code}")
    suspend fun verifyCode(@Path("code") code: String, @Body request: VerifyBody): VerifyCodeResponse

    @POST("/prod/login")
    suspend fun login(@Body request: LoginBody): LoginResponse
}