package com.pikipin.pkpn_mobile.network.registration.login

data class AuthenticationResult(
    val AccessToken: String,
    val ExpiresIn: Int,
    val IdToken: String,
    val RefreshToken: String,
    val TokenType: String
)