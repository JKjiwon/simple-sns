package com.lgtm.simplesns.domain.post.dto

import com.lgtm.simplesns.domain.post.entity.Post
import java.time.LocalDateTime

data class PostServiceDto(
    val id: Long,
    val memberId: Long,
    var contents: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun of(post: Post): PostServiceDto {
            return PostServiceDto(
                id = post.id!!,
                memberId = post.memberId,
                contents = post.contents,
                createdAt = post.createdAt!!,
                modifiedAt = post.modifiedAt!!
            )
        }
    }
}
