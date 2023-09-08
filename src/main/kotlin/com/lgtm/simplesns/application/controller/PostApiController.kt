package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.common.Api
import com.lgtm.simplesns.application.dto.PostDto
import com.lgtm.simplesns.application.usecase.CreatePostUsecase
import com.lgtm.simplesns.application.usecase.GetPostUsecase
import com.lgtm.simplesns.domain.post.dto.PostCreateCommand
import com.lgtm.simplesns.security.userdetail.MemberDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/posts")
@RestController
class PostApiController(
    private val createPostUsecase: CreatePostUsecase,
    private val getPostUsecase: GetPostUsecase
) {

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal principal: MemberDetails,
        @RequestBody command: PostCreateCommand
    ): Api<Any> {
        createPostUsecase.execute(principal.memberId, command)
        return Api.ok()
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): Api<PostDto> {
        val response = getPostUsecase.execute(id)
        return Api.ok(response)
    }
}