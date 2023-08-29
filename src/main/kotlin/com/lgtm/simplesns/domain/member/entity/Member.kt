package com.lgtm.simplesns.domain.member.entity

import com.lgtm.simplesns.domain.common.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var nickname: String,
    var email: String,
    var password: String,
    val birthday: LocalDate,

    @ElementCollection
    @CollectionTable(
        name = "MEMBER_ROLE",
        joinColumns = [JoinColumn(name = "member_id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val roles: List<Role> = listOf(Role.USER),
) : BaseEntity() {
    fun update(member: Member) {
        this.nickname = member.nickname
        this.email = member.email
    }
}