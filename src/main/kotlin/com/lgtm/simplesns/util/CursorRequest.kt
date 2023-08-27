package com.lgtm.simplesns.util

const val CURSOR_NONE_KEY = -1L

data class CursorRequest(
    val key: Long? = null,
    val size: Long,
) {
    fun hasKey(): Boolean {
        return key != null
    }

    fun next(nextKey: Long): CursorRequest {
        return CursorRequest(nextKey, size)
    }
}