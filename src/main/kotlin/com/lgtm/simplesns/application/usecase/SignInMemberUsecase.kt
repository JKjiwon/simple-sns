package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.common.ApplicationException
import com.lgtm.simplesns.application.common.ErrorCode
import com.lgtm.simplesns.application.dto.MemberSignInCommand
import com.lgtm.simplesns.security.jwt.JwtToken
import com.lgtm.simplesns.security.jwt.JwtUtils
import com.lgtm.simplesns.security.userdetail.MemberDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SignInMemberUsecase(
    private val jwtUtils: JwtUtils,
    private val authenticationManager: AuthenticationManager
) {

    fun execute(command: MemberSignInCommand): JwtToken {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(command.email, command.password)

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        } catch (e: AuthenticationException) {
            throw ApplicationException(ErrorCode.UNAUTHORIZED, "Fail to Sign-In", e)
        }

        SecurityContextHolder.getContext().authentication = authentication
        val principal = authentication.principal as MemberDetails
        return jwtUtils.create(principal.memberId)
    }
}