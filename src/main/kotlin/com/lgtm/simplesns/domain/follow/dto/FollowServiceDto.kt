package com.lgtm.simplesns.domain.follow.dto

import com.lgtm.simplesns.domain.follow.entity.Follow
import java.time.LocalDateTime

data class FollowServiceDto(
    val fromMemberId: Long,
    val toMemberId: Long,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(follow: Follow): FollowServiceDto {
            return FollowServiceDto(follow.fromMemberId, follow.toMemberId, follow.createdAt!!)
        }
    }
}