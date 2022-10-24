package com.pikipin.pkpn_mobile.network.registration.verify

import com.pikipin.pkpn_mobile.network.registration.Body

data class SendVerificationCodeResponse(
    val body: Body,
    val message: String
)