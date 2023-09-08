package com.lgtm.simplesns.utils.cursor

data class CursorResult<T>(
    val body: List<T>,
    val nextCursorRequest: CursorRequest
)
