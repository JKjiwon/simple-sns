package com.lgtm.simplesns.application.dto

import com.lgtm.simplesns.domain.follow.dto.FollowServiceDto
import com.lgtm.simplesns.domain.member.dto.MemberDto
import java.time.LocalDateTime

data class FollowMemberDto(
    val memberId: Long,
    val nickname: String,
    val relatedAt: LocalDateTime
) {
    companion object {
        fun of(follow: FollowServiceDto, member: MemberDto): FollowMemberDto {
            return FollowMemberDto(memberId = member.id, nickname = member.nickname, relatedAt = follow.createdAt)
        }
    }
}
