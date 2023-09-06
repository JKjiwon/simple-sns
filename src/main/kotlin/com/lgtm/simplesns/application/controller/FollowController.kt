package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.dto.FollowMemberDto
import com.lgtm.simplesns.application.usecase.CreateFollowUsecase
import com.lgtm.simplesns.application.usecase.GetFollowingUsecase
import com.lgtm.simplesns.security.userdetail.MemberDetails
import com.lgtm.simplesns.util.CursorRequest
import com.lgtm.simplesns.util.CursorResult
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/follow")
@RestController
class FollowController(
    private val createFollowUsecase: CreateFollowUsecase,
    private val getFollowingUsecase: GetFollowingUsecase
) {
    @PostMapping("/following/{toMemberId}")
    fun follow(
        @AuthenticationPrincipal principal: MemberDetails,
        @PathVariable toMemberId: Long
    ) {
        createFollowUsecase.execute(principal.memberId, toMemberId)
    }

    @GetMapping("/followings")
    fun getFollowing(
        @AuthenticationPrincipal principal: MemberDetails,
        @ModelAttribute cursorRequest: CursorRequest
    ): CursorResult<FollowMemberDto> {
        return getFollowingUsecase.execute(principal.memberId, cursorRequest)
    }
}