package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.PostDto
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.domain.post.dto.PostServiceDto
import com.lgtm.simplesns.domain.post.service.PostReadService
import org.springframework.stereotype.Service

@Service
class GetPostUsecase(
    val postReadService: PostReadService,
    val memberReadService: MemberReadService
) {
    fun execute(postId: Long): PostDto {
        val postServiceDto = postReadService.getPost(postId)
        val memberDto = memberReadService.getMember(postServiceDto.memberId)
        return toPostDto(postServiceDto, memberDto)
    }

    private fun toPostDto(post: PostServiceDto, member: MemberDto): PostDto {
        return PostDto(
            id = post.id,
            contents = post.contents,
            member = member,
            createdAt = post.createdAt,
            modifiedAt = post.modifiedAt
        )
    }
}