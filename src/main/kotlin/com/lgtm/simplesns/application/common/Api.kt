package com.lgtm.simplesns.application.common

class Api<T>(
    val code: Int,
    val message: String,
    val data: T?,
) {
    companion object {
        fun <T> ok(data: T): Api<T> {
            return of(ErrorCode.OK, data)
        }

        fun <T> of(errorCode: ErrorCode, data: T? = null): Api<T> {
            return Api(errorCode.code, errorCode.message, data)
        }
    }
}