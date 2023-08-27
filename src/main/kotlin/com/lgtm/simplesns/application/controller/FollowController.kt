package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.dto.FollowMemberDto
import com.lgtm.simplesns.application.usecase.CreateFollowUsecase
import com.lgtm.simplesns.application.usecase.GetFollowingUsecase
import com.lgtm.simplesns.util.CursorRequest
import com.lgtm.simplesns.util.CursorResult
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/follow")
@RestController
class FollowController(
    private val createFollowUsecase: CreateFollowUsecase,
    private val getFollowingUsecase: GetFollowingUsecase
) {
    @PostMapping("/{fromMemberId}/{toMemberId}")
    fun follow(@PathVariable fromMemberId: Long, @PathVariable toMemberId: Long) {
        createFollowUsecase.execute(fromMemberId, toMemberId)
    }

    @GetMapping("{memberId}/following")
    fun getFollowing(
        @PathVariable memberId: Long,
        @ModelAttribute cursorRequest: CursorRequest
    ): CursorResult<FollowMemberDto> {
        return getFollowingUsecase.execute(memberId, cursorRequest)
    }
}