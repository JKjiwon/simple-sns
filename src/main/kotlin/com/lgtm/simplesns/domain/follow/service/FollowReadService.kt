package com.lgtm.simplesns.domain.follow.service

import com.lgtm.simplesns.domain.follow.dto.FollowServiceDto
import com.lgtm.simplesns.domain.follow.entity.Follow
import com.lgtm.simplesns.domain.follow.repository.FollowRepository
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.util.CURSOR_NONE_KEY
import com.lgtm.simplesns.util.CursorRequest
import com.lgtm.simplesns.util.CursorResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class FollowReadService(
    private val followRepository: FollowRepository
) {
    fun getFollowing(member: MemberDto, cursorRequest: CursorRequest): CursorResult<FollowServiceDto> {
        val followings = getFollowings(cursorRequest, member)
        val nextKey = getNextKey(followings)
        return CursorResult(followings.map { FollowServiceDto.of(it) }, cursorRequest.next(nextKey))
    }

    private fun getFollowings(
        cursorRequest: CursorRequest,
        member: MemberDto
    ): List<Follow> {
        return if (cursorRequest.hasKey()) {
            followRepository.findAllByLessThanIdAndFromMemberIdAndOrderByIdDescLimitTo(
                cursorRequest.key!!,
                member.id,
                cursorRequest.size
            )
        } else {
            followRepository.findAllByFromMemberIdAndOrderByIdDescLimitTo(
                member.id,
                cursorRequest.size
            )
        }
    }

    private fun getNextKey(followings: List<Follow>) =
        followings.minOfOrNull { it.id!! } ?: CURSOR_NONE_KEY
}