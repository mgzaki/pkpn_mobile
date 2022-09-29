package com.pikipin.pkpn_mobile.network.login

data class Metadata(
    val attempts: Int,
    val httpStatusCode: Int,
    val requestId: String,
    val totalRetryDelay: Int
)