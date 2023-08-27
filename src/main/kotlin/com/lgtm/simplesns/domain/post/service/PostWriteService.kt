package com.lgtm.simplesns.domain.post.service

import com.lgtm.simplesns.domain.post.dto.PostCreateCommand
import com.lgtm.simplesns.domain.post.dto.PostServiceDto
import com.lgtm.simplesns.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PostWriteService(
    private val postRepository: PostRepository,
) {
    fun create(command: PostCreateCommand): PostServiceDto {
        val post = command.toEntity()
        val savePost = postRepository.save(post)
        return PostServiceDto.of(savePost)
    }
}