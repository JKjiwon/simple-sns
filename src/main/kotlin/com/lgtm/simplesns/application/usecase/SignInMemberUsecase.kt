package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.MemberSignInCommand
import com.lgtm.simplesns.security.jwt.JwtToken
import com.lgtm.simplesns.security.jwt.JwtUtils
import com.lgtm.simplesns.security.userdetail.MemberDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SignInMemberUsecase(
    private val jwtUtils: JwtUtils,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {

    fun execute(command: MemberSignInCommand): JwtToken {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(command.email, command.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(usernamePasswordAuthenticationToken)
        SecurityContextHolder.getContext().authentication = authentication

        val principal = authentication.principal as MemberDetails
        return jwtUtils.create(principal.memberId)
    }
}