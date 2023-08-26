package com.lgtm.simplesns.application.controller

import com.lgtm.simplesns.application.dto.PostDto
import com.lgtm.simplesns.application.usecase.GetPostUsecase
import com.lgtm.simplesns.domain.post.dto.PostCreateCommand
import com.lgtm.simplesns.domain.post.dto.PostServiceDto
import com.lgtm.simplesns.domain.post.service.PostWriteService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/posts")
@RestController
class PostController(
    private val postWriteService: PostWriteService,
    private val getPostUsecase: GetPostUsecase
) {

    @PostMapping
    fun createPost(@RequestBody command: PostCreateCommand): PostServiceDto {
        return postWriteService.create(command)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): PostDto {
        return getPostUsecase.execute(id)
    }

}