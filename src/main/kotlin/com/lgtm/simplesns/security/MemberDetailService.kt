package com.lgtm.simplesns.security

import com.lgtm.simplesns.domain.member.repository.MemberRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberDetailService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findByEmail(username)
            ?: throw BadCredentialsException("not found user with email: $username")
        return MemberDetails(member)
    }

    fun loadUserByMemberId(memberId: Long): UserDetails {
        val member = memberRepository.findById(memberId).orElseThrow {
            BadCredentialsException("not found user with memberId: $memberId")
        }
        return MemberDetails(member)
    }
}