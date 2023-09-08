package com.lgtm.simplesns.utils.redis

class RedisKeyGenerator {

    companion object {
        fun generateMemberTimeline(memberId: Long) : String {
            return "${RedisKey.MEMBER_TIMELINE}:${memberId}"
        }
    }
}
