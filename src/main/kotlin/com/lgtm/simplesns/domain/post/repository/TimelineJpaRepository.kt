package com.lgtm.simplesns.domain.post.repository

import com.lgtm.simplesns.domain.post.entity.Timeline
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TimelineJpaRepository : JpaRepository<Timeline, Long> {

    @Query("SELECT t FROM Timeline t WHERE t.memberId = :memberId ORDER BY t.id DESC LIMIT :size")
    fun findAllByMemberIdAndOrderByIdDescLimitTo(
        memberId: Long,
        size: Long
    ): List<Timeline>

    @Query("SELECT t FROM Timeline t WHERE t.id < :id AND t.memberId = :memberId ORDER BY t.id DESC LIMIT :size")
    fun findAllByLessThanIdAndMemberIdAndOrderByIdDescLimitTo(
        id: Long,
        memberId: Long,
        size: Long
    ): List<Timeline>

}