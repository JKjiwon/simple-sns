package com.lgtm.simplesns.domain.post.entity

import com.lgtm.simplesns.domain.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Timeline(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val memberId: Long,
    var postId: Long,
) : BaseEntity()