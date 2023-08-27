package com.lgtm.simplesns.domain.post.repository

import com.lgtm.simplesns.domain.post.entity.Timeline
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

const val TABLE_NAME = "timeline"

@Repository
class TimelineJdbcRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {

    fun saveAll(timelines: List<Timeline>) {
        val sql = """
                INSERT INTO $TABLE_NAME (member_id, post_id, created_at, modified_at)
                VALUES (:memberId, :postId, :createdAt, :modifiedAt)                
                """

        val params = timelines
            .map { BeanPropertySqlParameterSource(it) }
            .toTypedArray()

        namedParameterJdbcTemplate.batchUpdate(sql, params)
    }

}