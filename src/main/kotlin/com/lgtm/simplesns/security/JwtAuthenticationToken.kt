package com.lgtm.simplesns.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtAuthenticationToken(
    val jwtToken: String? = null,
    val principal: UserDetails? = null,
    authenticated: Boolean = false,
    authorities: MutableCollection<out GrantedAuthority> = mutableListOf()
) : AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = authenticated
    }

    companion object {
        @JvmStatic
        fun unauthenticated(
            jwtToken: String
        ): JwtAuthenticationToken {
            return JwtAuthenticationToken(jwtToken = jwtToken)
        }

        @JvmStatic
        fun authenticated(
            principal: UserDetails?,
            authorities: MutableCollection<out GrantedAuthority> = mutableListOf()
        ): JwtAuthenticationToken {
            return JwtAuthenticationToken(
                authenticated = true,
                authorities = authorities,
                principal = principal,
            )
        }
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): UserDetails? {
        return this.principal
    }
}