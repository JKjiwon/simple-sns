package com.lgtm.simplesns.domain.post.repository

import com.lgtm.simplesns.domain.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByIdIn(ids: Collection<Long>): List<Post>
}