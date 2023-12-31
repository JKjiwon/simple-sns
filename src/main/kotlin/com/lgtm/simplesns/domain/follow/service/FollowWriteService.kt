package com.lgtm.simplesns.domain.follow.service

import com.lgtm.simplesns.application.common.ApplicationException
import com.lgtm.simplesns.application.common.ErrorCode
import com.lgtm.simplesns.domain.follow.entity.Follow
import com.lgtm.simplesns.domain.follow.repository.FollowRepository
import com.lgtm.simplesns.domain.member.dto.MemberDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FollowWriteService(
    private val followRepository: FollowRepository
) {

    fun create(fromMember: MemberDto, toMember: MemberDto) {
        val follow = Follow(fromMemberId = fromMember.id, toMemberId = toMember.id)

        if (follow.fromMemberId == follow.toMemberId) {
            throw ApplicationException(ErrorCode.EQUAL_FOLLOWER_FOLLOWING)
        }

        followRepository.save(follow)
    }

}