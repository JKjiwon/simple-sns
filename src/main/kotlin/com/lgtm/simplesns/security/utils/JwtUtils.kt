package com.lgtm.simplesns.security.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.time.ZoneId
import java.util.*

@Component
class JwtUtils(
    val properties: JwtProperties
) {
    companion object {
        private const val CLAIM_MEMBER_ID_CODE = "memberId"
        private const val TOKEN_ISSUER = "com.simplesns"
    }

    private var signingKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secretKey))

    fun create(memberId: Long): JwtToken {
        val now = System.currentTimeMillis()
        val expiryDate = Date(now + properties.expirationMills)

        val claimsMap = mutableMapOf<String, String>()
        claimsMap[CLAIM_MEMBER_ID_CODE] = memberId.toString()

        val accessToken: String = Jwts.builder()
            .setIssuer(TOKEN_ISSUER)
            .setClaims(claimsMap)
            .setIssuedAt(Date(now))
            .setExpiration(expiryDate)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()

        return JwtToken(accessToken, expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
    }

    @Throws(
        JwtException::class
    )
    fun verify(accessToken: String): JwtVerificationResponse {
        val claims = Jwts.parserBuilder()
            .setSigningKey(signingKey).build().parseClaimsJws(accessToken).body

        return JwtVerificationResponse(
            memberId = claims[CLAIM_MEMBER_ID_CODE] as Long,
        )
    }
}