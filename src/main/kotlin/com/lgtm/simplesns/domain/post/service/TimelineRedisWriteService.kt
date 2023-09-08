package com.lgtm.simplesns.domain.post.service

import com.lgtm.simplesns.domain.post.entity.Timeline
import com.lgtm.simplesns.domain.post.repository.TimelineRedisRepository
import org.springframework.stereotype.Service

@Service
class TimelineRedisWriteService(
    private val timelineRedisRepository: TimelineRedisRepository
) {

    fun deliveryToTimeline(postId: Long, memberIds: List<Long>) {
        val timelines = memberIds.map { Timeline(postId = postId, memberId = it) }
        timelineRedisRepository.saveAll(timelines)
    }

}