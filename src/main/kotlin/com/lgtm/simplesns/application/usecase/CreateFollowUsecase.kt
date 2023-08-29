package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.domain.follow.service.FollowWriteService
import com.lgtm.simplesns.domain.member.service.MemberReadService
import org.springframework.stereotype.Service

@Service
class CreateFollowUsecase(
    private val memberReadService: MemberReadService,
    private val followWriteService: FollowWriteService
) {

    fun execute(fromMemberId: Long, toMemberId: Long) {
        val fromMember = memberReadService.getMember(fromMemberId)
        val toMember = memberReadService.getMember(toMemberId)

        followWriteService.create(fromMember, toMember)
    }
}