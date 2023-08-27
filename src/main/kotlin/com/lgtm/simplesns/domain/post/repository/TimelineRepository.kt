package com.lgtm.simplesns.domain.post.repository

import com.lgtm.simplesns.domain.post.entity.Timeline
import org.springframework.data.jpa.repository.JpaRepository

interface TimelineRepository : JpaRepository<Timeline, Long> {

}