package com.lgtm.simplesns.application.common

class ApplicationException(
    val errorCode: ErrorCode,
    message: String? = null,
    e: Throwable? = null
) : RuntimeException(message, e)