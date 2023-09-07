package com.lgtm.simplesns.application.exceptionhandler

import com.fasterxml.jackson.databind.ObjectMapper
import com.lgtm.simplesns.application.common.Api
import com.lgtm.simplesns.application.common.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    private val log = LoggerFactory.getLogger(AuthenticationEntryPoint::class.java)

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        log.error("", authException)

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val jsonResponse = Api.of<Any>(ErrorCode.UNAUTHORIZED)

        response.writer.write(objectMapper.writeValueAsString(jsonResponse))
    }
}