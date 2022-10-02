package com.pikipin.pkpn_mobile.network.login

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {

    @POST("/prod/register")
    suspend fun getVerificationCode(@Body request: RegisterBody): LoginResponse

    @POST("/prod/verify/{code}")
    suspend fun verifyCode(@Path("code") code: String, @Body request: VerifyBody): VerifyResponse
    
    suspend fun login(@Body requestBody: LoginBody)
}