package com.pikipin.pkpn_mobile.network.registration.verify

data class MetadataX(
    val attempts: Int,
    val httpStatusCode: Int,
    val requestId: String,
    val totalRetryDelay: Int
)