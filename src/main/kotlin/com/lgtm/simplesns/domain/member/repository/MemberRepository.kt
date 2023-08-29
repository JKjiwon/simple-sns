package com.lgtm.simplesns.domain.member.repository

import com.lgtm.simplesns.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findAllByIdIn(ids: List<Long>): List<Member>
}