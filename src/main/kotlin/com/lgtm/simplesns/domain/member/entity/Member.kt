package com.lgtm.simplesns.domain.member.entity

import com.lgtm.simplesns.domain.common.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_member", columnNames = ["email"])
    ]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var nickname: String,
    var email: String,
    var password: String? = null,
    val birthday: LocalDate? = null,

    @ElementCollection(fetch = FetchType.EAGER)
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