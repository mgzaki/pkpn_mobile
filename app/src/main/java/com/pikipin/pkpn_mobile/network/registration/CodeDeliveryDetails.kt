package com.pikipin.pkpn_mobile.network.login

data class CodeDeliveryDetails(
    val AttributeName: String,
    val DeliveryMedium: String,
    val Destination: String
)