package com.lgtm.simplesns.security.utils

import java.time.LocalDateTime

data class JwtToken(
    val accessToken: String,
    val expiration: LocalDateTime
)