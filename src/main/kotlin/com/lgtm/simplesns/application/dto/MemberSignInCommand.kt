package com.lgtm.simplesns.application.dto

data class MemberSignInCommand(
    val email: String,
    val password: String,
)