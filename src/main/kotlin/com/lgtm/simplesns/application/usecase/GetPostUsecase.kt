package com.lgtm.simplesns.application.usecase

import com.lgtm.simplesns.application.dto.PostDto
import com.lgtm.simplesns.domain.member.service.MemberReadService
import com.lgtm.simplesns.domain.post.service.PostReadService
import org.springframework.stereotype.Service

@Service
class GetPostUsecase(
    private val postReadService: PostReadService,
    private val memberReadService: MemberReadService,
) {
    fun execute(postId: Long): PostDto {
        val postServiceDto = postReadService.getPost(postId)
        val memberDto = memberReadService.getMember(postServiceDto.memberId)
        return PostDto.of(postServiceDto, memberDto)
    }
}