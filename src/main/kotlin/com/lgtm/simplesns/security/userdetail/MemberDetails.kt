package com.lgtm.simplesns.security.userdetail

import com.lgtm.simplesns.domain.member.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MemberDetails(val member: Member) : UserDetails {

    val memberId
        get() = member.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return member.roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()
    }

    override fun getPassword(): String {
        return member.password!!
    }

    override fun getUsername(): String {
        return member.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}