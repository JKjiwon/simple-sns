package com.lgtm.simplesns.utils

data class CursorResult<T>(
    val body: List<T>,
    val nextCursorRequest: CursorRequest
)
