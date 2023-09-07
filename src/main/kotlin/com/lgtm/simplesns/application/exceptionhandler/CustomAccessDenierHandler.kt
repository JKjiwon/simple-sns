package com.lgtm.simplesns.application.exceptionhandler

import com.fasterxml.jackson.databind.ObjectMapper
import com.lgtm.simplesns.application.common.Api
import com.lgtm.simplesns.application.common.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDenierHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    private val log = LoggerFactory.getLogger(CustomAccessDenierHandler::class.java)

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        log.error("", accessDeniedException)

        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val jsonResponse = Api.of<Any>(ErrorCode.FORBIDDEN)

        response.writer.write(objectMapper.writeValueAsString(jsonResponse))
    }
}