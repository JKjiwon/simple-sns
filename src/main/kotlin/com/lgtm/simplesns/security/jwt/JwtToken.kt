package com.lgtm.simplesns.security.jwt

import java.time.LocalDateTime

data class JwtToken(
    val accessToken: String,
    val expiration: LocalDateTime
)