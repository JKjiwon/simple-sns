package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.common.Api
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.dto.MemberUpdateCommand
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.domain.member.service.MemberWriteService
import com.lgtm.simplesns.security.userdetail.MemberDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/members")
@RestController
class MemberApiController(
    private val memberReadService: MemberReadService,
    private val memberWriteService: MemberWriteService,
) {

    @GetMapping("/me")
    fun getMe(
        @AuthenticationPrincipal principal: MemberDetails
    ): Api<MemberDto> {
        val response = memberReadService.getMember(principal.memberId)
        return Api.ok(response)
    }

    @PutMapping
    fun updateMember(
        @AuthenticationPrincipal principal: MemberDetails,
        @RequestBody command: MemberUpdateCommand
    ): Api<Any> {
        memberWriteService.update(principal.memberId, command)
        return Api.ok()
    }
}