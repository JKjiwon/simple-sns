package com.lgtm.simplesns.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer


@Profile("local")
@Configuration
class EmbeddedRedisConfig(
    redisProperty: RedisProperty
) {
    private val redisServer: RedisServer

    init {
        redisServer = RedisServer(redisProperty.port)
    }

    @PostConstruct
    fun startRedis() {
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}