package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.MemberSignUpCommand
import com.lgtm.simplesns.domain.member.dto.MemberCreateCommand
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.service.MemberWriteService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignUpMemberUsecase(
    private val passwordEncoder: PasswordEncoder,
    private val memberWriteService: MemberWriteService
) {

    fun execute(command: MemberSignUpCommand): MemberDto {
        val encodedPassword = passwordEncoder.encode(command.password)
        val memberCreateCommand = MemberCreateCommand(
            nickname = command.nickname,
            email = command.email,
            password = encodedPassword,
            birthday = command.birthday
        )
        return memberWriteService.create(memberCreateCommand)
    }

}