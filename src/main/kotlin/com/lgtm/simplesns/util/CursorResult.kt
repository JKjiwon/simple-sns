package com.lgtm.simplesns.util

data class CursorResult<T>(
    val body: List<T>,
    val nextCursorRequest: CursorRequest
)
