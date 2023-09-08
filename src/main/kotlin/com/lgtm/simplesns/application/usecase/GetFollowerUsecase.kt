package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.FollowMemberDto
import com.lgtm.simplesns.domain.follow.dto.FollowServiceDto
import com.lgtm.simplesns.domain.follow.service.FollowReadService
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.utils.CursorRequest
import com.lgtm.simplesns.utils.CursorResult
import org.springframework.stereotype.Service

@Service
class GetFollowerUsecase(
    private val followReadService: FollowReadService,
    private val memberReadService: MemberReadService
) {
    fun execute(memberId: Long, cursorRequest: CursorRequest): CursorResult<FollowMemberDto> {
        val member = memberReadService.getMember(memberId)
        val followers = followReadService.getFollowers(member, cursorRequest)

        val followerMembers = getFollowerMembers(followers)
        return CursorResult(followerMembers, followers.nextCursorRequest)
    }

    private fun getFollowerMembers(followers: CursorResult<FollowServiceDto>): List<FollowMemberDto> {
        if (followers.body.isEmpty()) {
            return listOf()
        }

        val followerMemberIds = followers.body.map { it.fromMemberId }
        val followerMembers = memberReadService.getMembers(followerMemberIds)
        val followerMemberIdMap = followerMembers.associateBy { it.id }
        return followers.body.map { FollowMemberDto.of(it, followerMemberIdMap[it.fromMemberId]!!) }
    }
}