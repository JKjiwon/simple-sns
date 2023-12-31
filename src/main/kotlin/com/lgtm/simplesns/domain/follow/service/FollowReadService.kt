package com.lgtm.simplesns.domain.follow.service

import com.lgtm.simplesns.domain.follow.dto.FollowServiceDto
import com.lgtm.simplesns.domain.follow.entity.Follow
import com.lgtm.simplesns.domain.follow.repository.FollowRepository
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.utils.CURSOR_NONE_KEY
import com.lgtm.simplesns.utils.CursorRequest
import com.lgtm.simplesns.utils.CursorResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class FollowReadService(
    private val followRepository: FollowRepository
) {
    fun getFollowings(member: MemberDto, cursorRequest: CursorRequest): CursorResult<FollowServiceDto> {
        val followings = findFollowings(member, cursorRequest)
        val nextKey = getNextKey(followings)
        return CursorResult(followings.map { FollowServiceDto.of(it) }, cursorRequest.next(nextKey))
    }

    fun getFollowers(member: MemberDto, cursorRequest: CursorRequest): CursorResult<FollowServiceDto> {
        val followers = findFollowers(member, cursorRequest)
        val nextKey = getNextKey(followers)
        return CursorResult(followers.map { FollowServiceDto.of(it) }, cursorRequest.next(nextKey))
    }

    // TODO: 팔로워 수가 많다면...대량 조회는 어떻게 해야하나...
    fun getFollowers(memberId: Long): List<FollowServiceDto> {
        return followRepository.findAllByToMemberId(memberId)
            .map { FollowServiceDto.of(it) }
    }

    private fun findFollowings(
        member: MemberDto,
        cursorRequest: CursorRequest
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

    private fun findFollowers(
        member: MemberDto,
        cursorRequest: CursorRequest
    ): List<Follow> {
        return if (cursorRequest.hasKey()) {
            followRepository.findAllByLessThanIdAndToMemberIdAndOrderByIdDescLimitTo(
                cursorRequest.key!!,
                member.id,
                cursorRequest.size
            )
        } else {
            followRepository.findAllByToMemberIdAndOrderByIdDescLimitTo(
                member.id,
                cursorRequest.size
            )
        }
    }

    private fun getNextKey(follows: List<Follow>) =
        follows.minOfOrNull { it.id!! } ?: CURSOR_NONE_KEY
}