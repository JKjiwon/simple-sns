package com.lgtm.simplesns.domain.post.service

import com.lgtm.simplesns.domain.post.dto.TimelineServiceDto
import com.lgtm.simplesns.domain.post.entity.Timeline
import com.lgtm.simplesns.domain.post.repository.TimelineJpaRepository
import com.lgtm.simplesns.utils.cursor.CURSOR_NONE_KEY
import com.lgtm.simplesns.utils.cursor.CursorRequest
import com.lgtm.simplesns.utils.cursor.CursorResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class TimelineReadService(
    private val timelineJpaRepository: TimelineJpaRepository
) {
    fun getTimeline(memberId: Long, cursorRequest: CursorRequest): CursorResult<TimelineServiceDto> {
        val timelines = findTimeline(memberId, cursorRequest)
        val nextKey = getNextKey(timelines)
        return CursorResult(timelines.map { TimelineServiceDto.of(it) }, cursorRequest.next(nextKey))
    }

    private fun findTimeline(memberId: Long, cursorRequest: CursorRequest): List<Timeline> {
        return if (cursorRequest.hasKey()) {
            timelineJpaRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDescLimitTo(
                cursorRequest.key!!,
                memberId,
                cursorRequest.size
            )
        } else {
            timelineJpaRepository.findAllByMemberIdAndOrderByIdDescLimitTo(memberId, cursorRequest.size)
        }
    }

    private fun getNextKey(timelines: List<Timeline>) =
        timelines.minOfOrNull { it.id!! } ?: CURSOR_NONE_KEY
}