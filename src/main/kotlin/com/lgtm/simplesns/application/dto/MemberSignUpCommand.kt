package com.lgtm.simplesns.application.dto

import java.time.LocalDate

data class MemberSignUpCommand(
    val nickname: String,
    val email: String,
    val password: String,
    val birthday: LocalDate
)