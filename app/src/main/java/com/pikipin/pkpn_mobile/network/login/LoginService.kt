package com.pikipin.pkpn_mobile.network.login

import retrofit2.http.Body
import retrofit2.http.GET

interface LoginService {
    @GET("/register")
    suspend fun getVerificationCode(@Body request: LoginBody): String
}