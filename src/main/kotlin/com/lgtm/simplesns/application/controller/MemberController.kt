package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.dto.MemberUpdateCommand
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.domain.member.service.MemberWriteService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/members")
@RestController
class MemberController(
    private val memberReadService: MemberReadService,
    private val memberWriteService: MemberWriteService,
) {

    @GetMapping("/{id}")
    fun getMember(@PathVariable id: Long): MemberDto {
        return memberReadService.getMember(id)
    }

    @PutMapping("/{id}")
    fun updateMember(@PathVariable id: Long, @RequestBody command: MemberUpdateCommand) {
        return memberWriteService.update(id, command)
    }
}