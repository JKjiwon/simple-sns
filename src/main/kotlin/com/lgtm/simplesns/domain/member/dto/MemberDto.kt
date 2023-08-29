package com.lgtm.simplesns.domain.member.dto

import com.lgtm.simplesns.domain.member.entity.Member
import java.time.LocalDate
import java.time.LocalDateTime

data class MemberDto(
    val id: Long,
    val nickname: String,
    val email: String,
    val birthday: LocalDate,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(member: Member): MemberDto {
            return MemberDto(
                id = member.id!!,
                nickname = member.nickname,
                email = member.email,
                birthday = member.birthday,
                createdAt = member.createdAt!!
            )
        }
    }
}