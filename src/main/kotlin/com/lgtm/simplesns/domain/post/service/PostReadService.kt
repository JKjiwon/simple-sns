package com.lgtm.simplesns.domain.post.service

import com.lgtm.simplesns.application.common.ApplicationException
import com.lgtm.simplesns.application.common.ErrorCode
import com.lgtm.simplesns.domain.post.dto.PostServiceDto
import com.lgtm.simplesns.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PostReadService(
    private val postRepository: PostRepository
) {
    fun getPost(id: Long): PostServiceDto {
        val post = postRepository.findById(id)
            .orElseThrow { ApplicationException(ErrorCode.POST_NOT_FOUND) }
        return PostServiceDto.of(post)
    }

    fun getPosts(ids: List<Long>): List<PostServiceDto> {
        val posts = postRepository.findAllByIdIn(ids)
        return posts.map { PostServiceDto.of(it) }
    }
}