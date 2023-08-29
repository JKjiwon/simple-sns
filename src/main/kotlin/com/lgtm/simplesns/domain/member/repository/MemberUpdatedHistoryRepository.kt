package com.lgtm.simplesns.domain.member.repository

import com.lgtm.simplesns.domain.member.entity.MemberUpdatedHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberUpdatedHistoryRepository : JpaRepository<MemberUpdatedHistory, Long>