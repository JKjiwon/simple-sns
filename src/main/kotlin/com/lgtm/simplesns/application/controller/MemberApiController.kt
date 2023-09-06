package com.lgtm.simplesns.application.controller

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
    ): MemberDto {
        return memberReadService.getMember(principal.memberId)
    }

    @PutMapping
    fun updateMember(
        @AuthenticationPrincipal principal: MemberDetails,
        @RequestBody command: MemberUpdateCommand
    ) {
        return memberWriteService.update(principal.memberId, command)
    }
}