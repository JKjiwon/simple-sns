package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.FollowMemberDto
import com.lgtm.simplesns.domain.follow.dto.FollowServiceDto
import com.lgtm.simplesns.domain.follow.service.FollowReadService
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.utils.CursorRequest
import com.lgtm.simplesns.utils.CursorResult
import org.springframework.stereotype.Service

@Service
class GetFollowingUsecase(
    private val followReadService: FollowReadService,
    private val memberReadService: MemberReadService
) {
    fun execute(memberId: Long, cursorRequest: CursorRequest): CursorResult<FollowMemberDto> {
        val member = memberReadService.getMember(memberId)
        val followings = followReadService.getFollowing(member, cursorRequest)

        val followMemberDtos = followMemberDtos(followings)
        return CursorResult(followMemberDtos, followings.nextCursorRequest)
    }

    private fun followMemberDtos(followings: CursorResult<FollowServiceDto>): List<FollowMemberDto> {
        if (followings.body.isEmpty()) {
            return listOf()
        }

        val followingMemberIds = followings.body.map { it.toMemberId }
        val members = memberReadService.getMembers(followingMemberIds)
        val memberIdMap = members.associateBy { it.id }
        return followings.body.map { toDto(it, memberIdMap[it.toMemberId]!!) }
    }

    private fun toDto(follow: FollowServiceDto, member: MemberDto): FollowMemberDto {
        return FollowMemberDto(memberId = member.id, nickname = member.nickname, createdAt = follow.createdAt)
    }
}