package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.domain.follow.service.FollowReadService
import com.lgtm.simplesns.domain.post.dto.PostCreateCommand
import com.lgtm.simplesns.domain.post.service.PostWriteService
import com.lgtm.simplesns.domain.post.service.TimelineWriteService
import org.springframework.stereotype.Service

@Service
class CreatePostUsecase(
    private val postWriteService: PostWriteService,
    private val followReadService: FollowReadService,
    private val timelineWriteService: TimelineWriteService
) {
    fun execute(memberId: Long, command: PostCreateCommand) {
        val post = postWriteService.create(memberId, command)

        // Timeline delivery : 개선 필요!!!
        val followers = followReadService.getFollowers(memberId)
        val followerIds = followers.map { it.fromMemberId }
        timelineWriteService.deliveryToTimeline(post.id, followerIds)
    }
}