package com.lgtm.simplesns.application.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val code: Int,
    val message: String
) {
    // common
    OK(HttpStatus.OK, 200, "Ok"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),

    // member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 1004, "Member Not Found"),

    // post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 2004, "Post Not Found"),

    // follow
    EQUAL_FOLLOWER_FOLLOWING(HttpStatus.BAD_REQUEST, 4011, "From, To 회원이 동일합니다."),
}