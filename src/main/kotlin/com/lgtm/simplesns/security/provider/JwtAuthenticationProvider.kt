package com.lgtm.simplesns.security.provider

import com.lgtm.simplesns.security.authentication.JwtAuthenticationToken
import com.lgtm.simplesns.security.jwt.JwtUtils
import com.lgtm.simplesns.security.jwt.JwtVerificationResponse
import com.lgtm.simplesns.security.userdetail.MemberDetailService
import io.jsonwebtoken.JwtException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider(
    private val jwtUtils: JwtUtils,
    private val memberDetailService: MemberDetailService
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val jwtAuthentication = authentication as JwtAuthenticationToken

        val verifyResponse: JwtVerificationResponse
        try {
            verifyResponse = jwtUtils.verify(jwtAuthentication.jwtToken!!)
        } catch (e: JwtException) {
            throw BadCredentialsException("Invalid jwtToken - accessToken: ${authentication.jwtToken}", e)
        }

        val principal = memberDetailService.loadUserByMemberId(verifyResponse.memberId)

        return JwtAuthenticationToken.authenticated(
            principal = principal,
            authorities = principal.authorities
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}