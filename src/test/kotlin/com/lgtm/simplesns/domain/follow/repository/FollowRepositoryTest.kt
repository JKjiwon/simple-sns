package com.lgtm.simplesns.domain.follow.repository

import com.lgtm.simplesns.domain.follow.entity.Follow
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class FollowRepositoryTest {
    @Autowired
    lateinit var followRepository: FollowRepository

    @AfterEach
    fun tearDown() {
        followRepository.deleteAllInBatch()
    }

    @DisplayName("회원id와 size로 follow리스트(회원id 역순)를 조회 할 수 있다.")
    @Test
    fun findByFromMemberIdAndOrderByIdDescLimitTo() {
        // given
        val follow1 = createFollow(1L, 2L)
        val follow2 = createFollow(1L, 3L)
        val follow3 = createFollow(1L, 4L)
        val follow4 = createFollow(2L, 3L)
        followRepository.saveAll(listOf(follow1, follow2, follow3, follow4))

        // when
        val result = followRepository.findAllByFromMemberIdAndOrderByIdDescLimitTo(1L, 2)

        // then
        assertThat(result).hasSize(2)
            .extracting("fromMemberId", "toMemberId")
            .containsExactly(
                tuple(1L, 4L),
                tuple(1L, 3L),
            )
    }

    @DisplayName("특정 걊 이하의 id값과 회원id와 size로 follow리스트(회원id 역순)를 조회 할 수 있다.")
    @Test
    fun findAllByLessThanIdAndFromMemberIdAndOrderByIdDescLimitTo() {
        // given
        val follow1 = createFollow(1L, 2L)
        val follow2 = createFollow(1L, 3L)
        val follow3 = createFollow(1L, 4L)
        val follow4 = createFollow(1L, 5L)
        followRepository.saveAll(listOf(follow1, follow2, follow3, follow4))

        val lastFollowId = followRepository.findAll().maxBy { it.id!! }.id!!

        // when
        val result = followRepository.findAllByLessThanIdAndFromMemberIdAndOrderByIdDescLimitTo(lastFollowId, 1L, 2)

        // then
        assertThat(result).hasSize(2)
            .extracting("fromMemberId", "toMemberId")
            .containsExactly(
                tuple(1L, 4L),
                tuple(1L, 3L),
            )
    }

    private fun createFollow(from: Long, to: Long) = Follow(fromMemberId = from, toMemberId = to)

}