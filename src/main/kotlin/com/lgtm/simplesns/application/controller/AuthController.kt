package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.dto.MemberSignInCommand
import com.lgtm.simplesns.application.dto.MemberSignUpCommand
import com.lgtm.simplesns.application.usecase.SignInMemberUsecase
import com.lgtm.simplesns.application.usecase.SignUpMemberUsecase
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.security.jwt.JwtToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/open-api/auth")
@RestController
class AuthController(
    private val signUpMemberUsecase: SignUpMemberUsecase,
    private val signInMemberUsecase: SignInMemberUsecase
) {
    @PostMapping("/sign-up")
    fun signUpMember(@RequestBody command: MemberSignUpCommand): MemberDto {
        return signUpMemberUsecase.execute(command)
    }

    @PostMapping("/sign-in")
    fun signInMember(@RequestBody command: MemberSignInCommand): JwtToken {
        return signInMemberUsecase.execute(command)
    }
}