package com.lgtm.simplesns.domain.follow.repository

import com.lgtm.simplesns.domain.follow.entity.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FollowRepository : JpaRepository<Follow, Long> {

    fun findAllByToMemberId(memberId: Long): List<Follow>

    @Query("SELECT f FROM Follow f WHERE f.fromMemberId = :fromMemberId ORDER BY f.id DESC LIMIT :size")
    fun findAllByFromMemberIdAndOrderByIdDescLimitTo(
        fromMemberId: Long,
        size: Long
    ): List<Follow>

    @Query("SELECT f FROM Follow f WHERE f.id < :id AND f.fromMemberId = :fromMemberId ORDER BY f.id DESC LIMIT :size")
    fun findAllByLessThanIdAndFromMemberIdAndOrderByIdDescLimitTo(
        id: Long,
        fromMemberId: Long,
        size: Long
    ): List<Follow>


    @Query("SELECT f FROM Follow f WHERE f.toMemberId = :toMemberId ORDER BY f.id DESC LIMIT :size")
    fun findAllByToMemberIdAndOrderByIdDescLimitTo(
        toMemberId: Long,
        size: Long
    ): List<Follow>

    @Query("SELECT f FROM Follow f WHERE f.id < :id AND f.toMemberId = :toMemberId ORDER BY f.id DESC LIMIT :size")
    fun findAllByLessThanIdAndToMemberIdAndOrderByIdDescLimitTo(
        id: Long,
        toMemberId: Long,
        size: Long
    ): List<Follow>
}