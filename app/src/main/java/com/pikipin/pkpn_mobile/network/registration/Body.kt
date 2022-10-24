package com.pikipin.pkpn_mobile.network.registration

data class Body(
    val `$metadata`: Metadata,
    val CodeDeliveryDetails: CodeDeliveryDetails,
    val UserConfirmed: Boolean,
    val UserSub: String
)