package com.pikipin.pkpn_mobile.network.registration

data class RegisterBody(
    val email: String,
    val password: String,
    val given_name: String,
    val phone_number: String
)
