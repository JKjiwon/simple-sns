package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.PostDto
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.domain.post.dto.PostServiceDto
import com.lgtm.simplesns.domain.post.dto.TimelineServiceDto
import com.lgtm.simplesns.domain.post.service.PostReadService
import com.lgtm.simplesns.domain.post.service.TimelineReadService
import com.lgtm.simplesns.utils.cursor.CursorRequest
import com.lgtm.simplesns.utils.cursor.CursorResult
import org.springframework.stereotype.Service

@Service
class GetTimelineUsecase(
    private val timelineReadService: TimelineReadService,
    private val postReadService: PostReadService,
    private val memberReadService: MemberReadService
) {
    fun execute(memberId: Long, cursorRequest: CursorRequest): CursorResult<PostDto> {
        val timelines = timelineReadService.getTimeline(memberId, cursorRequest)
        val posts = getPosts(timelines.body)
        return CursorResult(posts, timelines.nextCursorRequest)
    }

    private fun getPosts(timelines: List<TimelineServiceDto>): List<PostDto> {
        val postIds = timelines.map { it.postId }
        val posts = postReadService.getPosts(postIds).sortedByDescending { it.createdAt }

        val writerMap = getWriterMap(posts)
        return posts.map { PostDto.of(it, writerMap[it.memberId]!!) }
    }

    private fun getWriterMap(posts: List<PostServiceDto>): Map<Long, MemberDto> {
        val writerIds = posts.map { it.memberId }
        val writers = memberReadService.getMembers(writerIds)
        return writers.associateBy { it.id }
    }
}