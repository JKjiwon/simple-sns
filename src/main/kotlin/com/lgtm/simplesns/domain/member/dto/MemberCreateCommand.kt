package com.lgtm.simplesns.domain.member.dto

import com.lgtm.simplesns.domain.member.entity.Member
import java.time.LocalDate

data class MemberCreateCommand(
    val nickname: String,
    val email: String,
    val birthday: LocalDate
) {
    fun toEntity(): Member {
        return Member(
            nickname = nickname,
            email = email,
            birthday = birthday
        )
    }
}