package com.lgtm.simplesns.application.dto

import com.lgtm.simplesns.domain.member.dto.MemberDto
import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    val member: MemberDto,
    var contents: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)
