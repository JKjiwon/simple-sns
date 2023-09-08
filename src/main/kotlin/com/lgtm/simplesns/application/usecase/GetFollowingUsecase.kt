package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.FollowMemberDto
import com.lgtm.simplesns.domain.follow.dto.FollowServiceDto
import com.lgtm.simplesns.domain.follow.service.FollowReadService
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.utils.cursor.CursorRequest
import com.lgtm.simplesns.utils.cursor.CursorResult
import org.springframework.stereotype.Service

@Service
class GetFollowingUsecase(
    private val followReadService: FollowReadService,
    private val memberReadService: MemberReadService
) {
    fun execute(memberId: Long, cursorRequest: CursorRequest): CursorResult<FollowMemberDto> {
        val member = memberReadService.getMember(memberId)
        val followings = followReadService.getFollowings(member, cursorRequest)

        val followingMembers = getFollowMembers(followings)
        return CursorResult(followingMembers, followings.nextCursorRequest)
    }

    private fun getFollowMembers(followings: CursorResult<FollowServiceDto>): List<FollowMemberDto> {
        if (followings.body.isEmpty()) {
            return listOf()
        }

        val followingMemberIds = followings.body.map { it.toMemberId }
        val followingMembers = memberReadService.getMembers(followingMemberIds)
        val followingMemberIdMap = followingMembers.associateBy { it.id }
        return followings.body.map { FollowMemberDto.of(it, followingMemberIdMap[it.toMemberId]!!) }
    }
}