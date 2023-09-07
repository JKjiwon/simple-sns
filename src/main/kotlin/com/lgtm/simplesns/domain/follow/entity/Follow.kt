package com.lgtm.simplesns.domain.follow.entity

import com.lgtm.simplesns.domain.common.BaseEntity
import jakarta.persistence.*
import org.springframework.util.Assert

@Entity
class Follow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val fromMemberId: Long,
    val toMemberId: Long,
) : BaseEntity()