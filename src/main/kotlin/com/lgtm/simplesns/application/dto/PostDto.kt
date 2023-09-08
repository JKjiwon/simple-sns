package com.lgtm.simplesns.application.dto

import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.post.dto.PostServiceDto
import java.time.LocalDateTime

data class PostDto(
    val postId: Long,
    val member: MemberDto,
    var contents: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun of(post: PostServiceDto, member: MemberDto): PostDto {
            return PostDto(
                postId = post.id,
                contents = post.contents,
                member = member,
                createdAt = post.createdAt,
                modifiedAt = post.modifiedAt
            )
        }
    }
}
