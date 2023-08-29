package com.lgtm.simplesns.domain.post.repository

import com.lgtm.simplesns.domain.post.entity.Timeline
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class TimelineRepositoryTest {

    @Autowired
    lateinit var timelineRepository: TimelineRepository

    @Autowired
    lateinit var timelineJdbcRepository: TimelineJdbcRepository

    @AfterEach
    fun tearDown() {
        timelineRepository.deleteAllInBatch()
    }

    @Test
    fun batchInsert() {
        // given
        val timeline1 = createTimeline(1, 1)
        val timeline2 = createTimeline(1, 2)
        val timeline3 = createTimeline(1, 3)
        timelineJdbcRepository.saveAll(listOf(timeline1, timeline2, timeline3))

        // when
        val timelines = timelineRepository.findAll()

        // then
        assertThat(timelines).hasSize(3)
            .extracting("memberId", "postId")
            .contains(
                tuple(1L, 1L),
                tuple(1L, 2L),
                tuple(1L, 3L),
            )
    }

    @Test
    fun batchInsertWithEmptyList() {
        // given
        timelineJdbcRepository.saveAll(listOf())

        // when
        val timelines = timelineRepository.findAll()

        // then
        assertThat(timelines).hasSize(0)
    }

    private fun createTimeline(memberId: Long, postId: Long): Timeline {
        return Timeline(memberId = memberId, postId = postId)
    }
}