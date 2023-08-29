package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.dto.PostDto
import com.lgtm.simplesns.application.usecase.CreatePostUsecase
import com.lgtm.simplesns.application.usecase.GetPostUsecase
import com.lgtm.simplesns.domain.post.dto.PostCreateCommand
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/posts")
@RestController
class PostController(
    private val createPostUsecase: CreatePostUsecase,
    private val getPostUsecase: GetPostUsecase
) {

    @PostMapping
    fun createPost(@RequestBody command: PostCreateCommand) {
        createPostUsecase.execute(command)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): PostDto {
        return getPostUsecase.execute(id)
    }

}