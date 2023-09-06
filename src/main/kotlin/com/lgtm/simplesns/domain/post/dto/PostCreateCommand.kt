package com.lgtm.simplesns.domain.post.dto

import com.lgtm.simplesns.domain.post.entity.Post

data class PostCreateCommand(
    val contents: String
) {
    fun toEntity(memberId: Long): Post {
        return Post(memberId = memberId, contents = contents)
    }
}
