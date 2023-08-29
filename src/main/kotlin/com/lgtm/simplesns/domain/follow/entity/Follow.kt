package com.lgtm.simplesns.domain.follow.entity

import com.lgtm.simplesns.domain.common.BaseEntity
import jakarta.persistence.*
import org.springframework.util.Assert

@Entity
@Table(
    name = "follow",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_follow", columnNames = ["fromMemberId", "toMemberId"])
    ]
)

class Follow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val fromMemberId: Long,
    val toMemberId: Long,
) : BaseEntity() {
    init {
        Assert.isTrue(fromMemberId != toMemberId, "From, To 회원이 동일합니다.")
    }
}