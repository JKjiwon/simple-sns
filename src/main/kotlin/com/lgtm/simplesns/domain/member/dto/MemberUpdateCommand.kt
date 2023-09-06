package com.lgtm.simplesns.domain.member.dto

import com.lgtm.simplesns.domain.member.entity.Member

data class MemberUpdateCommand(
    val nickname: String,
    val email: String,
) {
    fun toEntity(): Member {
        return Member(
            nickname = nickname,
            email = email
        )
    }
}