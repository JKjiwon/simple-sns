package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.common.Api
import com.lgtm.simplesns.application.dto.FollowMemberDto
import com.lgtm.simplesns.application.usecase.CreateFollowUsecase
import com.lgtm.simplesns.application.usecase.GetFollowerUsecase
import com.lgtm.simplesns.application.usecase.GetFollowingUsecase
import com.lgtm.simplesns.security.userdetail.MemberDetails
import com.lgtm.simplesns.utils.cursor.CursorRequest
import com.lgtm.simplesns.utils.cursor.CursorResult
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/follow")
@RestController
class FollowApiController(
    private val createFollowUsecase: CreateFollowUsecase,
    private val getFollowingUsecase: GetFollowingUsecase,
    private val getFollowerUsecase: GetFollowerUsecase
) {
    @PostMapping("/following/{toMemberId}")
    fun follow(
        @AuthenticationPrincipal principal: MemberDetails,
        @PathVariable toMemberId: Long
    ): Api<Any> {
        createFollowUsecase.execute(principal.memberId, toMemberId)
        return Api.ok()
    }

    @GetMapping("/followings")
    fun getFollowing(
        @AuthenticationPrincipal principal: MemberDetails,
        @ModelAttribute cursorRequest: CursorRequest
    ): Api<CursorResult<FollowMemberDto>> {
        val response = getFollowingUsecase.execute(principal.memberId, cursorRequest)
        return Api.ok(response)
    }

    @GetMapping("/followers")
    fun getFollowers(
        @AuthenticationPrincipal principal: MemberDetails,
        @ModelAttribute cursorRequest: CursorRequest
    ): Api<CursorResult<FollowMemberDto>> {
        val response = getFollowerUsecase.execute(principal.memberId, cursorRequest)
        return Api.ok(response)
    }
}