package com.lgtm.simplesns.domain.post.repository

import com.lgtm.simplesns.domain.post.entity.Timeline
import com.lgtm.simplesns.utils.redis.RedisKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Repository
import java.time.ZoneId

@Repository
class TimelineRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    @Suppress("UNCHECKED_CAST")
    fun saveAll(timelines: List<Timeline>) {
        val keySerializer = redisTemplate.keySerializer as StringRedisSerializer
        val valueSerializer = redisTemplate.valueSerializer as GenericToStringSerializer<Any>
        redisTemplate.executePipelined { connection ->
            for (timeline in timelines) {
                connection.zSetCommands().zAdd(
                    keySerializer.serialize(RedisKeyGenerator.generateMemberTimeline(timeline.memberId)),
                    timeline.createdAt!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toDouble(),
                    valueSerializer.serialize(timeline.postId),
                )
            }
            null
        }
    }

    fun findAllByMemberId(memberId: Long, size: Long): List<Timeline> {
        return redisTemplate.opsForZSet()
            .reverseRangeWithScores(RedisKeyGenerator.generateMemberTimeline(memberId), 0, size - 1)
            ?.map { Timeline(memberId = memberId, postId = it.value.toString().toLong()) }
            ?: listOf()
    }
}