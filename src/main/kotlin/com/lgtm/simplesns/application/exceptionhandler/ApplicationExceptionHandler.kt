package com.lgtm.simplesns.application.exceptionhandler

import com.lgtm.simplesns.application.common.Api
import com.lgtm.simplesns.application.common.ApplicationException
import com.lgtm.simplesns.application.common.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApplicationExceptionHandler {

    private val log = LoggerFactory.getLogger(ApplicationExceptionHandler::class.java)

    @ExceptionHandler(value = [ApplicationException::class])
    fun applicationException(
        exception: ApplicationException
    ): ResponseEntity<Api<Any>> {
        log.error("", exception)

        val errorCode = exception.errorCode

        return ResponseEntity
            .status(errorCode.httpStatus)
            .body(
                Api.of(errorCode)
            )
    }

    @ExceptionHandler(value = [Exception::class])
    fun exception(
        exception: Exception
    ): ResponseEntity<Api<Any>> {
        log.error("", exception)

        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR

        return ResponseEntity
            .status(errorCode.httpStatus)
            .body(
                Api.of(errorCode)
            )
    }
}