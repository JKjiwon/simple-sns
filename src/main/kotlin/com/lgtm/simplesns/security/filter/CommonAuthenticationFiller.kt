package com.lgtm.simplesns.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

abstract class CommonAuthenticationFiller(
    protected val authenticationManager: AuthenticationManager,
    private val requestMatcher: RequestMatcher = AntPathRequestMatcher("/**")
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            if (!isRequestMatched(request)) {
                filterChain.doFilter(request, response)
                return
            }

            val result = attemptAuthentication(request, response)
            SecurityContextHolder.getContext().authentication = result
        } catch (e: AuthenticationException) {
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(request, response)
    }

    private fun isRequestMatched(request: HttpServletRequest): Boolean {
        return requestMatcher.matches(request)
    }

    abstract fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication

}