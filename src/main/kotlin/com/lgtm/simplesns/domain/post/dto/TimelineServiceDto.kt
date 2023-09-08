package com.lgtm.simplesns.domain.post.dto

import com.lgtm.simplesns.domain.post.entity.Timeline

data class TimelineServiceDto(
    val postId: Long,
    val memberId: Long,
) {
    companion object {
        fun of(timeline: Timeline): TimelineServiceDto {
            return TimelineServiceDto(
                postId = timeline.postId,
                memberId = timeline.memberId
            )
        }
    }
}