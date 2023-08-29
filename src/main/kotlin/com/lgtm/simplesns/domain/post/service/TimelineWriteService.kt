package com.lgtm.simplesns.domain.post.service

import com.lgtm.simplesns.domain.post.entity.Timeline
import com.lgtm.simplesns.domain.post.repository.TimelineJdbcRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class TimelineWriteService(
    private val timelineJdbcRepository: TimelineJdbcRepository
) {

    fun deliveryToTimeline(postId: Long, memberIds: List<Long>) {
        val timelines = memberIds.map { Timeline(postId = postId, memberId = it) }
        timelineJdbcRepository.saveAll(timelines)
    }
}