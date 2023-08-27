package com.lgtm.simplesns.application.dto

import java.time.LocalDateTime

data class FollowMemberDto(
    val memberId: Long,
    val nickname: String,
    val createdAt: LocalDateTime
)
