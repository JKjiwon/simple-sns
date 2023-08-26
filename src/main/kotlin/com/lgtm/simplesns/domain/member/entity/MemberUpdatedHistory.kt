package com.lgtm.simplesns.domain.member.entity

import com.lgtm.simplesns.domain.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class MemberUpdatedHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var nickname: String,
    var email: String
) : BaseEntity()