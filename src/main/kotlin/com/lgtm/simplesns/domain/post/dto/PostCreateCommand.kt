package com.lgtm.simplesns.domain.post.dto

import com.lgtm.simplesns.domain.post.entity.Post

data class PostCreateCommand(
    val memberId: Long,
    val contents: String
) {
    fun toEntity(): Post {
        return Post(memberId = memberId, contents = contents)
    }
}
