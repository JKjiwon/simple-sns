package com.lgtm.simplesns.domain.member.service

import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberReadService(
    private val memberRepository: MemberRepository
) {

    fun getMember(memberId: Long): MemberDto {
        val member = memberRepository.findById(memberId).orElseThrow()
        return MemberDto.of(member)
    }

}